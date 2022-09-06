const amqplib = require('amqplib')
const processarPagamento = require("./ProcessarPagamento")

const conectar = async () => {
    const conn = await amqplib.connect("amqps://lbozpljt:I21HWRfbuiSZzvX3ewyECJwXLTKMmmj7@jackal.rmq.cloudamqp.com/lbozpljt");
    const conexao = await conn.createChannel();
    return conexao;
}

const consumirFilaProcessamento = async () => {
    const conexao = await conectar();
    conexao.consume("processar", async (msg) => {
        try {
            const mensagem = JSON.parse(msg.content);
            console.log(mensagem);
            await postarFilaAtualizacao(conexao, { id: mensagem.id, status: processarPagamento() })
            conexao.ack(msg);
        } catch {
            conexao.nack(msg);
            console.log("Erro");
        }
    })
}

const postarFilaAtualizacao = async (conexao, mensagem) => {
    console.log(mensagem);
    conexao.publish('pagamento', 'pagamento.atualizar', Buffer.from(JSON.stringify(mensagem)));
}

consumirFilaProcessamento();




