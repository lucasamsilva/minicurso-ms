module.exports = () => {
    const valor = Math.random();
    if(valor < 0.5) {
        return "PAGAMENTO_APROVADO";
    } else if(valor < 0.8) {
        return "PAGAMENTO_RECUSADO";
    } else {
        throw new Error();
    }
}