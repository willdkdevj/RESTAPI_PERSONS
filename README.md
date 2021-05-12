## API REST para o Gerenciamento de um Cadastro de Pessoas
> O projeto consiste na criação de uma API REST utilizando os principais conceitos arquiteturais desenvolvidas através do Desenvolvimento Orientado a Testes (TDD). Na qual utiliza das facilidades do Spring Boot a fim de agilizar a construção dos códigos, até o seu deploy na nuvem utilizando a plataforma Heroku.

[![Spring Badge](https://img.shields.io/badge/-Spring-brightgreen?style=flat-square&logo=Spring&logoColor=white&link=https://spring.io/)](https://spring.io/)
[![Maven Badge](https://img.shields.io/badge/-Maven-000?style=flat-square&logo=Apache-Maven&logoColor=white&link=https://maven.apache.org/)](https://maven.apache.org/)
[![Heroku Badge](https://img.shields.io/badge/-Heroku-purple?style=flat-square&logo=Heroku&logoColor=white&link=https://id.heroku.com/)](https://id.heroku.com/)


<img align="right" width="400" height="300" src="https://github.com/willdkdevj/assets/blob/main/Spring/spring-framework.png">

## Descrição da Aplicação
A aplicação consiste em uma API (*Application Programming Interface*) REST (*Representational State Transfer*), sendo aplicado o modelo cliente/servidor na qual tem a função de enviar e receber dados através do protocolo HTTP, sendo o seu principal objetivo permitir a interoperabilidade entre aplicações distintas. Desta forma, esta aplicação emula um serviço web que interage com um serviço de banco de dados a fim de criar, coletar, manipular e excluir seus registros, com um serviço web externo, podendo este ser outra API, sistema Web ou mobile, entre outros, desde que este atenda aos padrões estipulados pela arquitetura REST.

No decorrer deste documento é apresentado com mais detalhes sua implementação, descrevendo como foi desenvolvida a estrutura da API, suas dependências e como foi colocado em prática o TDD para a realização dos testes unitários dos metodos na camada de negócio. Como foi implementado o Spring Boot, para agilizar a construção do código e sua configuração, conforme os *starters* e as suas dependências. Assim como, o Spring Data JPA, que nos dá diversas funcionalidades permitindo uma melhor dinâmica nas operações com bancos de dados e sua manutenção. Até o seu deploy na plataforma Heroku para disponibilizá-la pela nuvem ao cliente.

## Principais Frameworks
Os frameworks são pacotes de códigos prontos que facilita o desenvolvimento de aplicações, desta forma, utilizamos estes para obter funcionalidades para agilizar a construção da aplicação. Abaixo segue os frameworks utilizados para o desenvolvimento este projeto:

**Pré-Requisito**: Java 11 (11.0.10 2021-01-19 LTS)
			   Maven 3.6.3

| Framework       | Versão |
|-----------------|:------:|
| Spring Boot     | 2.4.4  |
| Spring Actuator | 2.4.4  |
| Spring Data JPA | 2.4.4  |
| Hibernate       | 6.1.7  |
| Lombok          | 1.18.18|
| MapStruct       | 1.4.1  |
| JUnit 	      | 5.7.1  |
| Mockito         | 3.6.28 |
| Swagger         | 2.9.2  |


## Sobre a Estrutura da API REST utilizando o TDD na Construção
Durante o desenvolvimento é essencial garantir que a API funcione corretamente, desta forma, é de vital importância testá-la para corrigir possíveis problemas antes de chegar ao usuário final. Desta forma, utilizando o conceito de Desenvolvimento Orientado a Testes (Test Driven Development - TDD) foi aplicado o conceito de ciclos de testes a fim de criar testes, fazê-los passar de alguma forma e refatorá-los a fim de melhorar sua legibilidade, para desta forma, construir as funcionalidades lógicas necessárias para automação do processo.

![Life Of Cicle - TDD](https://github.com/willdkdevj/assets/blob/main/Metodology/tdd_cicle.gif)

Os testes foram realizados unitariamente aos Recursos, que identificam de modo único os objetos através da URI definidos no *Controller*, e aos Métodos, que são as lógicas de negócios definidos no *Service*, utilizada para obter os dados necessários da camada de dados. Para este fim, foram criadas Entidades Construtoras que simulam as Entidades que representam um objeto *Model* da camada de dados, isto é possível graças ao ao framework do Lombok com a anotação @Builder.

![Framework Project - Test](https://github.com/willdkdevj/assets/blob/main/Spring/framework_test_person.png)

Os testes unitários são utilizados para testar a menor unidade do projeto de software de modo isolado, seguindo a mesma lógica e com uso de dados similares que seriam utilizados na produção para testar a unidade em questão, que no caso, são os métodos das classes Controller e Service do modelo MVC.

Toda esta dinâmica é possível através do framework JUnit, que possibilita a criação de classes de testes que contêm métodos de verificação das lógicas presentes aos mêtodos que das classes devem ser expostos em "produção", permitindo organizá-los de forma hierárquica, seradados ou até mesmo todos de uma vez. O objetivo desta abordagem é evitar códigos desnecessários e a duplicidade obtendo um código funcional e testado contra falhas agregando substancialmente mais qualidade.

### Exemplo do Uso do TDD para Construção da Lógica dos Métodos
O Junit utiliza-se de anotações (Annotations) para indicar se o método é de teste ou não, se o método deve ser executado antes ou depois de um determinado código, se o teste deve ou não ser ignorado, se a classe em questão é uma suite de teste, entre diversas outras funcionalidades que o framework nos permite configurar.

Para realização dos testes utilizaremos a versão 5 do JUnit, para isto foi informado no **pom.xml** para excluir a versão 4 que automamente ele assume a versão posterior para o projeto.

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
	<scope>test</scope>
	<exclusions>
		<exclusion>
			<groupId>org.junit.vintage</groupId>
			<artifactId>junit-vintage-engine</artifactId>
		</exclusion>
	</exclusions>
</dependency>
```

Esta versão contém os novos recursos para construção de testes usando o JUnit, fornecendo uma implementação de ``TestEngine`` para execução dos testes, onde a classe [Assert] com seus métodos de verificação foram substituídos pela classe [Assertions] que possuí implementações dos métodos de verificação, mais com uma semântica mais refinada.

### A Implementação dos Testes na Classe Service
Para demonstrar como foi realizado o uso do conceito TDD com o framework abaixo vou apresentar o que foi realizado para construção do método registerPerson() na classe de ``Service`` MVC. Mas conforme foi explanado anteriormente, foi necessário criar objetos para simular as classes em entidades com dados estáticos para emular entradas de informações aos objetos com classes construtoras (*builders*), que se trata de classes com valores estáticos para seus atributos, seguindo os conceitos do DTO. E para realizar esta conversão de uma classe DTO em uma entidade, foi utilizado o framework ``MapStruct``. Ele simplifica o mapeamento de objetos DTO para objetos de Entidade permitindo gerar código com base em uma abordagem de conversão utilizando uma interface.

Para realizar esta abordagem é utilizada a anotação @Mapper na interface que mapeia quais são os objetos a serem convertidos atraves da sobreescritas de seus métodos.

```java
@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
    Person toModel(PersonDTO personDTO);

    PersonDTO toDTO(Person person);
}
```

Neste caso especifico, foi necessário informar ao MapStruct que tipo de dado o objeto DTO está passando para o objeto Bean, que neste caso, é um atríbuto LocalDate, enquanto o DTO é uma String. Desta forma, a anotação @Mapping atrela estes campos distintos informando o formato do dado.

Agora que está esclarecido como os dados dos objetos serão utilizados pelos testes e seus objetos mocados, vamos para a clase de teste de Serviço (**Service**). Antes de mais nada, foi anotada a classe de teste com a anotação @ExtendWith(MockitoExtension.class) que injeta nesta classe a biblioteca do ``Mockito`` a fim de permitir *mocar* objetos em nossa classe utilizando mais duas anotações @Mock e @InjectMocks. O objetivo de mocar objetos é criar objetos dublês que simulam o comportamento de objetos reais de forma controlada.

```java
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private PersonMapper mapper = PersonMapper.INSTANCE;

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

```

Observe que "mocamos" objetos PersonRepository e PersonService para inserirmos no contexto da classe de teste Mockito, além disso, criamos uma instância constante de Mapper através do PersonMapper, para realizarmos a conversão de objetos a serem utilizados nos testes utilizando de suas facilidades de já instanciar os objetos e atribuir valores aos seus atríbutos.

Agora, depois de todos estes passos, vamos ao método registerPerson() para realizar o teste inicial. O conceito do TDD preconiza que o teste tem que falhar antes de realizar a construção lógica para tentar validá-lo. Desta forma, foi criado o método testPersonDTOProvidedThenReturnSavedMessage() na qual é anotado com @Test a fim de informar que é um método de teste ao JUnit e executamos sem implementação alguma, só para ocorrer a falha. Depois foi implementado o código com a lógica necessária para fazê-lo passar. E finalmente, o mesmo é refatorado a fim de torná-lo mais "limpo", deixando-o mais legível.

```java
	@Test
    void testPersonDTOProvidedThenReturnSavedMessage() {
    	// given
        PersonDTO personDTO = PersonDTO.builder().build().toPersonDTO();
        Person convertedPerson = mapper.toModel(personDTO);

        // When
        when(repository.save(any(Person.class))).thenReturn(convertedPerson);

        // Then
        MessageResponseDTO expectedSuccessMessage = service.registerPerson(personDTO);
        MessageResponseDTO expectedSuccessMessageID = createInspectMessageResponse(convertedPerson.getId());

        assertEquals(expectedSuccessMessageID, expectedSuccessMessage);
    }
```
Desta forma, em ``given`` é o que parâmetro fornecido ao método, que recebe um objeto do tipo PersonDTO, e o que é retornado após a invocação do método da JPA para salvar o conteúdo presente no objeto em ``when``, que no caso o método save() recebe um Bean convertido do DTO e retorna novamente um Bean do objeto passado. Na qual em ``then`` é realizado a contraprova ao checar os objetos retornados ao invocar o método através do objeto mocado de Service (service.registerPerson()), em comparação com uma Classe de Construção para testes (MessageBuilder) a fim confirmar se a resposta serão iguais, confirmadas através do método do JUnit **assertEquals**.

Este processo de verificação é realizado para testar os returnos esperados pela aplicação, assim como, as eventuais exceções a serem tratadas para devolutiva ao usuário.  

### A Implementação dos Testes na Classe Controller
O processo no ``Controller`` é bem similar, na qual também é anotada a classe de teste com a anotação @ExtendWith(MockitoExtension.class) a fim de permitir *mocar* objetos em nossa classe através das anotações @Mock e @InjectMocks. O diferencial é a declaração da classe MockMvc com o objetivo de validar os *endpoints*.

```java
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    private static final String URL_PATH = "/api/v1/people";

    private MockMvc mockMvc;

    @InjectMocks
    private PersonControllerImpl controller;

    @Mock
    private PersonService service;
```

O [MockMvc] permite automatizar o processo de verificação dos endpoints ao invocar e checar o retorno das requisições conforme o tipo de dado esperado. Desta forma, utilizamos a anotação @BeforeEach para que seja instanciado um objeto **Controller**, passando este objeto como parâmetro para contrução de um *setup* MockMvc, assim como, o tipo de retorno a ser apresentado pela *view*. Desta forma, cada método que realizará o teste utilizará um objeto MockMvc com a estrutura de dados a ser validado conforme o ``endpoint`` a ser testado.

```java
@BeforeEach
void setUp() {
    controller = new PersonControllerImpl(service);
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
            .build();
}
```

Agora o método de teste retornará uma instância subjacente da classe ``DispatcherServlet``, desta forma, informando o tipo de operação, ao passar o MockHttpServletRequestBuilder usando o método estático MockMvcRequestBuilders.post informando como parâmetro a constante declarada no início da classe, permite a criação de um pedido customizado. Este é passado ao método mockMVC.perform(), no qual ele é usado para criar um objeto MockHttpServletRequest que é usado para definir um ponto inicial para o teste.

```java
@Test
void testWhenPOSTIsCalledThenAPersonShouldBeCreated() throws Exception {
    PersonDTO personDTO = PersonBuilder.builder().build().toPersonDTO();
    MessageResponseDTO messageResponseDTO = createInspectMessageResponse(personDTO.getId());

    when(service.registerPerson(personDTO)).thenReturn(messageResponseDTO);

    mockMvc.perform(post(URL_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonToString(personDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message", is(messageResponseDTO.getMessage())));
}
```

Também foi passado para o objeto MockMvc o tipo de conteúdo (*Content Type*) a ser retornado, e para tratar o objeto DTO a fim de ser compatível com o Content Type é utilizado o método ``jsonToString`` criado para realizar a conversão necessária, por fim, é específicado o tipo de estado (*MockMvcResultMatchers*) e o retorno aguardado pelo endpoint.

O método mockMvc.perform() retorna um objeto do tipo *ResultAction*, é um objeto utilizado para assegurar o resultado do teste, similar a forma usada pelo método ``assertEquals`` do JUnit. Desta maneira, é possível realizar a checagem do status HTTP, este caso o retorno OK (HTTP.200), e que a próxima view será “signin”.

## A Hospedagem na Plataforma Heroku

## Como Está Documentado o Projeto
O framework ``Swagger UI`` auxilia na criação da documentação do projeto, por possuir uma variedade de ferramentas que permite modelar a descrição, consumo e visualização de serviços da API REST. No projeto foi incluída suas dependências (Swagger-UI, Swagger-Core) para habilitá-lo para uso na aplicação, desta forma, no *snippet* abaixo é apresentado o Bean principal para sua configuração, presente na classe SwaggerConfig.

```java
@Bean
public Docket api() {
return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(apis())
        .paths(PathSelectors.any())
        .build()
        .apiInfo(constructorApiInfo());
}
```

A especificação da API consiste na determinação de parâmetros de identificação e os modelos de dados que serão aplicados pela API, além de suas funcionalidades. Entretanto, o Swagger por padrão lista todos os endpoints retornando os códigos 200, 201, 204,401,403 e 404, mas é possível especificar quais são os códigos do protocolo HTTP que sua aplicação utiliza ao utilizar a anotação @ApiResponses.

![Framework Project - Test](https://github.com/willdkdevj/assets/blob/main/Spring/swagger_panel_person.png)

O método apis() permite utilizar a classe **RequestHandlerSelectors** para filtrar qual é o pacote base (*basePackage*) a fim de ser listado apenas os seus endpoints. Já o método apiInfo() possibilita inserir parâmetros para descrever dados de informação sobre a aplicação e seu desenvolvedor. Desta forma, o framework Swagger possibilita documentar a API REST de um modo ágil de eficiente as suas funcionalidades. Sua exposição é feita através do link <http://localhost:8080/swagger-ui.html>

## Como Executar o Projeto

```bash
# Para clonar o repositório do projeto, através do terminal digite o comando abaixo
git clone https://github.com/willdkdevj/RESTAPI_PERSONS.git

# Para acessar o diretório do projeto digite o comando a seguir
cd /RESTAPI_PERSONS

# Executar projeto via terminal, digite o seguinte comando
./mvnw spring-boot:run

# Para Executar a suíte de testes desenvolvidas, basta executar o seguinte comando
./mvnw clean test
```

## Agradecimentos
Obrigado por ter acompanhado aos meus esforços para desenvolver este Projeto utilizando a estrutura do Spring para criação de uma API REST 100% funcional, utilizando os recursos do Spring data JPA para facilitar as consultas, o padrão DTO para inclusão e atualização dos dados, além de, listar grandes quantidades de dados paginas, com ordenação e busca, utilizando os conceitos do TDD para implementar testes de integração para validar nossos endpoints com o MockMVC e gerar a documentação de forma automática através do Swagger! :octocat:

Como diria um velho mestre:
> *"Cedo ou tarde, você vai aprender, assim como eu aprendi, que existe uma diferença entre CONHECER o caminho e TRILHAR o caminho."*
>
> *Morpheus - The Matrix*