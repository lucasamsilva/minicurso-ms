# Semana de Informática IFSP Birigui 2022  🖥️

## Microsserviços: como são construídas aplicações modernas, resilientes e escaláveis.
Microsserviços são uma abordagem arquitetônica e organizacional do desenvolvimento de software na qual o software consiste em pequenos serviços independentes que se comunicam usando APIs bem definidas. 
Nesse minicurso serão apresentados e implementados conceitos que são utilizados nessa arquitetura como por exemplo processamento assíncrono por meio de filas, balanceamento de carga, API Gateway, service discovery e padrões REST.
## API
Será construída uma API que simula o funcionamento de um e-commerce, sendo possível fazer operações básicas como visualização dos produtos disponíveis em lista, busca de um produto específico e realização de compras.

## Tecnologias Utilizadas
- Java 11
- Spring Boot
- Node.JS
- RabbitMQ

## Fluxos de processamento
### Fluxo de pedido
Nesse fluxo é possível realizar uma compra utilizando a API construída.

![enter image description here](https://i.imgur.com/H3z97jT.png)

### cURL
```
curl --request POST \
  --url http://localhost:8090/vendas/v1/pedidos \
  --header 'Content-Type: application/json' \
  --data '{
	"produtos": [
		{
			"id": 1,
			"quantidade": 2
		},
		{
			"id": 3,
			"quantidade": 2
		},
		{
			"id": 7,
			"quantidade": 1
		}
	]
}'
```

## Fluxo Lista de Produtos
Nesse fluxo é possível visualizar uma lista dos produtos disponíveis para compra.
![enter image description here](https://i.imgur.com/0oOFaKt.png)


### cURL
```
curl --request GET \
  --url http://localhost:8090/estoque/v1/produtos
```
## Fluxo Busca de Produto
Nesse fluxo é possível buscar um produto único utilizando seu identificador(id).
![enter image description here](https://i.imgur.com/ULJTz3h.png)

### cURL
```
curl --request GET \
  --url http://localhost:8090/estoque/v1/produtos/{id}
```

# Configurações Necessárias
## Java
Necessário instalar a JDK 11 para o sistema operacional utilizado, download disponível em: [Java Archive Downloads - Java SE 11 | Oracle Brasil](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)

## Maven
 É necessário que a máquina utilizada tenha o Maven instalado, caso não tenha é possível realizar a instalação seguindo o passo a passo presente no link: [How to Install Maven on Windows, Linux, and Mac | Baeldung](https://www.baeldung.com/install-maven-on-windows-linux-mac)

 **Alternativa**
 É possível executar o Maven por meio apenas do diretório onde ele foi baixado seguindo os passos:
 

 1. Realizar download do arquivo compactado(.zip) do Maven no site oficial: [Maven – Download Apache Maven](https://maven.apache.org/download.cgi)
 2. Descompactar o arquivo em um local de sua preferência
 3. Executar os comandos maven no CMD utilizando o arquivo "mvn" presente dentro da pasta "bin" do seu maven descompactado
 Ex.: Supondo que o maven tenha sido descompactado no diretório `C:/Downloads` utilizar o caminho `C:/Downloads/apache-maven-{sua-versao-maven}/bin/mvn` para executar os comandos. Para validar essa solução basta executar o comando utilizando o *path* do seu arquivo `mvn` seguido do sufixo `-v` ("`C:/Downloads/apache-maven-{sua-versao-maven}/bin/mvn" -v`), deve ser exibido na tela a versão do Maven que foi feito o *Download*.

## RabbitMQ
É possível criar instâncias RabbitMQ gratuitamente no site [CloudAMQP - RabbitMQ as a Service](https://www.cloudamqp.com/), sendo necessário apenas selecionar o plano gratuito no momento da criação.

**Alternativa**
É possível executar o RabbitMQ em um container docker, mais instruções podem ser encontradas em:
- [rabbitmq - Official Image | Docker Hub](https://registry.hub.docker.com/_/rabbitmq/)
- [Downloading and Installing RabbitMQ — RabbitMQ](https://www.rabbitmq.com/download.html)

## Cliente HTTP
Para realização das chamadas HTTP é necessário uma aplicação que permita a execução de requisições HTTP. É possível utilizar qualquer uma das duas mais populares Insomnia ou Postman, podendo ser facilmente feito o download e instalação em seus sites oficiais:
- [Download - Insomnia](https://insomnia.rest/download)
- [Download Postman | Get Started for Free](https://www.postman.com/downloads/)

# Executando os projetos
É recomendável que para cada uma das aplicações a seguir os passos sejam seguidos em CMD's diferentes, pois dessa maneira a aplicação estará funcionando por completo.
## Eureka
Acesse o diretório `/eureka` por meio do CMD e execute os seguinte comandos:

 - `mvn package`
 - `cd target`
 - `java -jar eureka-0.0.1-SNAPSHOT.jar`

A aplicação deve iniciar na porta 8761, sendo acessível em [`http://localhost:8761`](http://localhost:8761)

## Gateway
Acesse o diretório `/gateway` por meio do CMD e execute os seguinte comandos:

 - `mvn package`
 - `cd target`
 - `java -jar gateway-0.0.1-SNAPSHOT.jar`

A aplicação deve iniciar na porta 8090, sendo acessível em [`http://localhost:8090`](http://localhost:8090).

**Observação**: ao acessar a aplicação será apresentado uma tela com uma mensagem escrito `Whitelabel Error Page`

## Estoque
Acesse o diretório `/estoque` por meio do CMD e execute os seguinte comandos:

 - `mvn package`
 - `cd target`
 - `java -jar estoque-0.0.1-SNAPSHOT.jar`

A aplicação iniciará em uma porta aleatória e se registrará no Eureka, sendo possível certificar sua correta execução acessando [`http://localhost:8761`](http://localhost:8761).

## Vendas
Para execução dessa aplicação é necessário ter uma instância RabbitMQ criada e substituir as informações de host e autenticação corretamente nos comandos a seguir para definição de variáveis de ambiente. Lembrando que o comando `set` é válido para o sistema operacional Windows, em caso de utilização de outro sistema é necessário utilizar o comando de variáveis de ambiente específico do sistema.
Acesse o diretório `/vendas` por meio do CMD e execute os seguinte comandos:

- `set host={seu_host_rabbitmq}`
- `set virtualHost={seu_virtual_host_rabbitmq}`
 - `set username={seu_username_rabbitmq}`
 - `set virtualHost={sua_senha_rabbitmq}`
 - `mvn package`
 - `cd target`
 - `java -jar vendas-0.0.1-SNAPSHOT.jar`

A aplicação iniciará em uma porta aleatória e se registrará no Eureka, sendo possível certificar sua correta execução acessando [`http://localhost:8761`](http://localhost:8761).

## Pagamentos
Para execução dessa aplicação é necessário ter uma instância RabbitMQ criada e substituir as informações de host  corretamente nos comandos a seguir para definição de variáveis de ambiente. Lembrando que o comando `set` é válido para o sistema operacional Windows, em caso de utilização de outro sistema é necessário utilizar o comando de variáveis de ambiente específico do sistema.
Acesse o diretório `/pagamento` por meio do CMD e execute os seguinte comandos:

- `set RABBIT_HOST={seu_host_rabbitmq}` 
**Observação**: (deve ser informado no formato `amqps://{username}:{password}@{host}/{virtual_host}`)`
 - `npm install`
 - `node index.js`

A aplicação iniciará e ficará aguardando mensagens do rabbitMQ.
