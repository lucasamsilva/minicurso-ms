const amqplib = require('amqplib')
const processarPagamento = require("./ProcessarPagamento")
const rabbitHost = process.env.RABBIT_HOST;

const conectar = async () => {
    const conn = await amqplib.connect(rabbitHost);
    return conn.createChannel();
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