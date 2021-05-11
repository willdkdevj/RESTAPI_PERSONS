## API REST para o Gerenciamento de um Cadastro de Pessoas
> O projeto consiste na criação de uma API REST utilizando os principais conceitos arquiteturais desenvolvidas através do Desenvolvimento Orientado a Testes (TDD). Na qual utiliza das facilidades do Spring Boot a fim de agilizar a construção dos códigos, até o seu deploy na nuvem utilizando a plataforma Heroku.

[![Spring Badge](https://img.shields.io/badge/-Spring-brightgreen?style=flat-square&logo=Spring&logoColor=white&link=https://spring.io/)](https://spring.io/)
[![Maven Badge](https://img.shields.io/badge/-MAVEN-000?style=flat-square&logo=MAVEN&logoColor=white&link=https://maven.apache.org/)](https://maven.apache.org/)
[![Heroku Badge](https://img.shields.io/badge/-Heroku-purple?style=flat-square&logo=Heroku&logoColor=white&link=https://id.heroku.com/)](https://id.heroku.com/)


<img align="right" width="400" height="300" src="https://matheuspcarvalhoblog.files.wordpress.com/2018/05/spring-framework.png">

## Descrição da Aplicação
A aplicação consiste em uma API (*Application Programming Interface*) REST (*Representational State Transfer*), sendo aplicado o modelo cliente/servidor na qual tem a função de enviar e receber dados através do protocolo HTTP, sendo o seu principal objetivo permitir a interoperabilidade entre aplicações distintas. Desta forma, esta aplicação emula um serviço web que interage com um serviço de banco de dados a fim de criar, coletar, manipular e excluir seus registros, com um serviço web externo, podendo este ser outra API, sistema Web ou mobile, entre outros, desde que este atenda aos padrões estipulados pela arquitetura REST.

No decorrer deste documento é apresentado com mais detalhes sua implementação, descrevendo como foi desenvolvida a estrutura da API, suas dependências e como foi colocado em prática o TDD para a realização dos testes unitários dos metodos na camada de negócio. Como foi implementado o Spring Boot, para agilizar a construção do código e sua configuração, conforme os *starters* e as suas dependências. Assim como, o Spring Data JPA, que nos dá diversas funcionalidades permitindo uma melhor dinâmica nas operações com bancos de dados e sua manutenção. Até o seu deploy na plataforma Heroku para disponibilizá-la pela nuvem ao cliente.

## Sobre a Estrutura da API REST utilizando o TDD na Construção
Durante o desenvolvimento é essencial garantir que a API funcione corretamente, desta forma, é de vital importância testá-la para corrigir possíveis problemas antes de chegar ao usuário final. Desta forma, utilizando o conceito de Desenvolvimento Orientado a Testes (Test Driven Development - TDD) foi aplicado o conceito de ciclos de testes a fim de criar testes, fazê-los passar de alguma forma e refatorá-los a fim de melhorar sua legibilidade, para desta forma, construir as funcionalidades lógicas necessárias para automação do processo.

Os testes foram realizados unitariamente aos Recursos, que identificam de modo único os objetos através da URI definidos no *Controller*, e aos Métodos, que são as lógicas de negócios definidos no *Service*, utilizada para obter os dados necessários da camada de dados. Para este fim, foram criadas Entidades Construtoras que simulam as Entidades que representam um objeto *Model* da camada de dados, isto é possível graças ao ao framework do Lombok com a anotação @Builder.

Os testes unitários são utilizados para testar a menor unidade do projeto de software de modo isolado, seguindo a mesma lógica e com uso de dados similares que seriam utilizados na produção para testar a unidade em questão, que no caso, são os métodos das classes Controller e Service do modelo MVC.

Toda esta dinâmica é possível através do framework JUnit, que possibilita a criação de classes de testes que contêm métodos de verificação das lógicas presentes aos mêtodos que das classes devem ser expostos em "produção", permitindo organizá-los de forma hierárquica, seradados ou até mesmo todos de uma vez. O objetivo desta abordagem é evitar códigos desnecessários e a duplicidade obtendo um código funcional e testado contra falhas agregando substancialmente mais qualidade.

### Exemplo do Uso do TDD para Construção da Lógica dos Métodos
O Junit utiliza-se de anotações (Annotations) para indicar se o método é de teste ou não, se o método deve ser executado antes ou depois de um determinado código, se o teste deve ou não ser ignorado, se a classe em questão é uma suite de teste, entre diversas outras funcionalidades que o framework nos permite configurar.

Para realização dos testes utilizaremos a versão 5 do JUnit, para isto foi informado no ``pom.xml`` para excluir a versão 4 que automamente ele assume a versão posterior para o projeto.
```sh
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

Para demonstrar como foi realizado o uso do conceito TDD com o framework abaixo vou apresentar o que foi realizado para construção do método registerPerson() na classe de ``Service`` MVC. Desta forma, conforme o conceito é criado um teste a fim para atender a necessidade do método da forma mais simples possível. Mas conforme foi explanado anteriormente, foram criados objetos para simular as classes em entidades com dados estáticos para emular entradas de informações aos objetos com classes construtoras (*builders*) e para realizar a conversão de uma classe DTO em uma entidade foi utilizado o framework ``MapStruct``. Ele simplifica o mapeamento de objetos DTO para objetos de Entidade permitindo gerar código com base em uma abordagem de conversão.

Para realizar esta abordagem é utilizada a anotação @Mapper na interface que mapeia quais são os objetos a serem convertidos atraves da sobreescritas de seus métodos.
```sh
@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
    Person toModel(PersonDTO personDTO);

    PersonDTO toDTO(Person person);
}
```

Neste caso especifico, foi necessário informar ao MapStruct que tipo de dado o objeto DTO está passando para o objeto Bean, que neste caso, é um atríbuto LocalDate, enquanto o DTO é uma String. Desta forma, a anotação @Mapping atrela estes campos distintos informando o formato do dado.

Agora que está esclarecido como os dados dos objetos serão utilizados pelos testes e seus objetos mocados, vamos para a clase de teste de Serviço (``Service``). Antes de mais nada, foi anotada a classe de teste com a anotação @ExtendWith(MockitoExtension.class) que injeta nesta classe a biblioteca do ``Mockito`` a fim de permitir *mocar* objetos em nossa classe utilizando mais duas anotações @Mock e @InjectMocks. O objetivo de mocar objetos é criar objetos dublês que simulam o comportamento de objetos reais de forma controlada.
```sh
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
```sh
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
Desta forma, em ``given`` é o que parâmetro fornecido ao método, que recebe um objeto do tipo PersonDTO, e o que é retornado após a invocação do método da JPA para salvar o conteúdo presente no objeto em ``when``, que no caso o método save() recebe um Bean convertido do DTO e retorna novamente um Bean do objeto passado. Na qual em ``then`` é realizado a contraprova ao checar os objetos retornados ao invocar o método através do objeto mocado de Service (service.registerPerson()), em comparação com uma Classe de Construção para testes (MessageBuilder) a fim confirmar se a resposta serão iguais, confirmadas através do método do JUnit ``assertEquals``.

Este processo de verificação é realizado para testar os returnos esperados pela aplicação, assim como, as eventuais exceções a serem tratadas para devolutiva ao usuário.  

### A Implementação dos Testes na Classe Controller
O processo no ``Controller`` é bem similar, na qual também é anotada a classe de teste com a anotação @ExtendWith(MockitoExtension.class) a fim de permitir *mocar* objetos em nossa classe através das anotações @Mock e @InjectMocks. O objetivo de mocar objetos é criar objetos dublês que simulam o comportamento de objetos reais de forma controlada.

```sh
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    private static final String URL_PATH = "/api/v1/people";

    private MockMvc mockMvc;

    @InjectMocks
    private PersonControllerImpl controller;

    @Mock
    private PersonService service;
```

Observe que também declaramos um objeto do tipo [MockMvc] a fim de construir um objeto do tipo MockHttpServletRequest para definir um ponto inicial para testes. Desta forma, utilizamos a anotação @BeforeEach para que este método seja chamado antes de cada instanciação de um objeto teste. Abaixo segue como foi implementado o método para construção do objeto MockMvc
```sh
@BeforeEach
void setUp() {
    controller = new PersonControllerImpl(service);
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
            .build();
}
```

Desta vez, o próximo passo é criar algo chamado MockHttpServletRequestBuilder usando o método estático MockMvcRequestBuilders.get(…) e um padrão Builder. Ele é passado ao método mockMVC.perform(…), no qual ele é usado para criar um objeto MockHttpServletRequest que é usado para definir um ponto inicial para o teste. Nesse teste, tudo que fiz foi passar a url “/posts” e configurar a entrada como “any” (qualquer) para o tipo de mídia. Você pode configurar vários outros atributos de objetos de requisição, usando métodos como contentType(), contextPath(), cookie() etc. Para mais informações, veja o Spring javadoc para MockHttpServletRequest.

O método mockMvc.perform() retorna um objeto ResultAction. Isso parece ser um “wrapper” para o MvcResult atual. O ResultsActions é um conveniente objeto usado para assegurar o resultado do teste, da mesma forma que os métodos assertEquals(…) do JUnit ou verify(…) do Mockito. Nesses casos, estou checando que o resultado do status HTTP é ok (ex:. 200) e que a próxima view será “signin”.

A diferença entre esse teste e a versão apenas do Mockito é que você não está diretamente testando o resultado da chamada do método pela sua instância de teste; você está testando o objeto HttpServletResponse que a chamada para o seu método gera.