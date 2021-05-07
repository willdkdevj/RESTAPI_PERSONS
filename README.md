## API REST para o Gerenciamento de um Cadastro de Pessoas
> O projeto consiste na criação de uma API REST utilizando os principais conceitos arquiteturais desenvolvidas através do Desenvolvimento Orientado a Testes (TDD). Na qual utilizando as facilidades do Spring Boot a fim de agilizar a construção dos códigos, até o seu deploy na nuvem utilizando a plataforma Heroku.

[![Spring Badge](https://img.shields.io/badge/-Spring-brightgreen?style=flat-square&logo=Spring&logoColor=white&link=https://spring.io/)](https://spring.io/)
[![Maven Badge](https://img.shields.io/badge/-MAVEN-000?style=flat-square&logo=MAVEN&logoColor=white&link=https://maven.apache.org/)](https://maven.apache.org/)
[![Heroku Badge](https://img.shields.io/badge/-Heroku-purple?style=flat-square&logo=Heroku&logoColor=white&link=https://id.heroku.com/)](https://id.heroku.com/)


<img align="right" width="400" height="300" src="https://matheuspcarvalhoblog.files.wordpress.com/2018/05/spring-framework.png">

## Descrição da Aplicação
A aplicação consiste em uma API (*Application Programming Interface*) REST (*Representational State Transfer*), sendo aplicado o modelo cliente/servidor na qual tem a função de enviar e receber dados através do protocolo HTTP, sendo o seu principal objetivo permitir a interoperabilidade entre aplicações distintas. Desta forma, esta aplicação emula um serviço web que interage com um serviço de banco de dados a fim de criar, coletar, manipular e excluir seus registros, com um serviço web externo, podendo este ser outra API, sistema Web ou mobile, entre outros, desde que este atenda aos padrões estipulados pela arquitetura REST.

No decorrer deste documento é apresentado com mais detalhes sua implementação, descrevendo como foi desenvolvida a estrutura da API, suas dependências e como foi colocado em prática o TDD para a realização dos testes unitários dos metodos na camada de negócio. Como foi implementado o Spring Boot, para agilizar a construção do código e sua configuração, conforme os *starters* e as suas dependências. Assim como, o Spring Data JPA, que nos dá diversas funcionalidades permitindo uma melhor dinâmica nas operações com bancos de dados e sua manutenção. Até o seu deploy na plataforma Heroku para disponibilizá-la pela nuvem ao cliente.

## Sobre a Estrutura da API REST utilizando o TDD na Construção
Durante o desenvolvimento é essencial garantir que a API funciona corretamente, desta forma, é de vital importância testá-la para corrigir possíveis problemas antes de chegar ao usuário final. Desta forma, utilizando o conceito de Desenvolvimento Orientado a Testes (Test Driven Development - TDD) foi aplicado o conceito de ciclos de testes a fim de fazer o teste passar e refatorá-lo a fim de melhorar sua legibilidade.

Os testes foram realizados unitariamente aos Recursos, que identificam de modo único os objetos através da URI definidos no *Controller*, e aos Métodos, que são as lógicas de negócios definidos no *Service*, utilizada para obter os dados necessários da camada de dados. Para este fim, foram criadas Entidades Construtoras que simulam as Entidades que representam um objeto *Model* da camada de dados.

Através da API do JUnit foram construídos os testes unitários por fornecer um conjunto de funcionalidades que permitem verificar cada unidade de código, facilitar a automação dos testes e obter os resultados. 

O objetivo é evitar códigos desnecessários e a duplicidade obtendo um código funcional e testado contra falhas agregando substancialmente mais qualidade.

O Apache Maven é uma ferramenta de apoio a equipes que trabalham com projeto Java (mas não se restringe somente a Java), possibilitando a desenvolvedores a automatizar, gerenciar e padronizar a construção e publicação de suas aplicações, permitindo maior agilidade e qualidade ao produto.
Abaixo são apresentadas as etapas para importá-lo a IDE IntelliJ, mas também é possível trabalhar com outras IDE's como Eclipsse, NetBeans, entre outras, podendo ser diferente os procedimentos realizados.