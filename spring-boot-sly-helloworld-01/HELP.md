## 一、快速构建 Spring Boot 应用

### 1.实验介绍

**实验内容**
我们将在这一节学习如何快速构建一个 Spring Boot 项目，之后会对 Spring Boot 项目的目录结构进行简单的介绍，最后是启动 Spring Boot 项目的方法介绍。

**实验知识点**

- 如何快速构建一个 Spring Boot 项目
- Spring Boot 项目目录结构介绍
- Spring Boot 项目启动

**实验环境**

- JDK 1.8 或者更高版本
- Spring Boot 2.1.0-RELEASE
- Maven 3+

### 2.Spring Boot 项目构建

IDEA --> 使用 Spring Initializr 构建

### 3.Spring Boot 项目启动

#### Main() 方法启动

与普通的 Web 项目相比，Spring Boot 启动项目减少了几个中间步骤，不用去配置 Servlet 容器，也不用打包并且发布到 Servlet 容器再去启动，而是直接运行主方法即可启动项目，开发调试都十分方便也节省开发时间。在你的本机上开发项目时，可以直接在 Eclipse 或者 IDEA 中运行 Spring Boot 主程序类即可，比如现在的项目中有 Application 类，可以直接运行它的 run() 方法，项目就能够正常启动了。

#### Maven 插件启动

由于 pom.xml 文件中引入了 `spring-boot-maven-plugin` 插件依赖，也可以直接使用 Maven 命令来启动 Spring Boot 项目，插件配置如下。如果 pom.xml 文件中没有该 Maven 插件，是无法通过这种方式启动 Spring Boot 项目的，这一点需要注意。

```xml
<build>
    <plugins>
        <plugin>      <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

输入命令 `mvn spring-boot:run` ,通过maven启动Spring Boot应用

#### java -jar 命令启动

项目初始化时我们选择的打包方式为 Jar ，因此项目开发完成进行打包时的结果是一个 Jar 包， Java 运行 Jar 包的命令为 `java -jar xxx.jar`

## 二、Spring Boot项目开发之web项目开发讲解

**实验内容**

我们将在这一节学习 Spring Boot 中如何进行 web 相关功能的开发，主要包括常用的接口开发和 Spring Boot 对于静态资源文件的处理。

**实验知识点**

- DispatchServlet 配置
- Spring Boot 如何处理静态 web 资源
- 消息转换器 HttpMessageConverter

**实验环境**

- JDK 1.8 或者更高版本
- Spring Boot 2.1.0-RELEASE
- Maven 3+

### 1.Hello Spring Boot

因此我们自行实现一个 Controller 来测试一下 Spring Boot 如何处理 web 请求，接下来使用 Spring Boot 做一个简单的接口实现。在 `src/main/java` 目录下新建 `com.itheng.controller` 包，之后在包里新建一个 HelloController 类，编码如下:

```java
package com.itheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author PV-CZH mail:zilemontree@yeah.net
 * @description Hello Spring Boot
 * @Date 2020/12/10 16:57
 * @title: HelloController
 * @projectName spring-boot-shiyanlou-projects
 */
@Controller
public class HelloController {
    /**
     * 发送一个hello请求
     * @return
     */
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello,Spring Boot!";
    }
}
```

### 2.DispatchServlet 自动配置

我们创建了第一个 Spring Boot 项目，在初始化时我们选择了 web 模块(即在 pom 文件中引入了 `spring-boot-starter-web` 依赖)，该模块中包含了 SpringMVC 相关依赖，之后我们开发了第一个 web 功能，仅仅是在控制器类中实现了一个简单的字符串返回方法，并对该方法进行了请求地址映射设置，之后没有做其他配置操作，但是项目启动后就能够进行正常的 web 请求并正常返回数据。 咱们来回想一下在未使用 Spring Boot 开发 web 应用的场景，在平时的 Spring web 项目中，使用 SpringMVC 模块时需要在容器中 SpringMVC 相关的配置 bean，并且在 web.xml 中配置 DispatcherServlet 以及它的 进行请求地址的映射，配置文件如下:

```xml
    <!--Start spring mvc Servlet-->
    <servlet>
        <servlet-name>Spring</Servlet-name>
        <servlet-class>org.springframework.web.Servlet.DispatcherServlet</Servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--End spring mvc Servlet-->
    <!--Start Servlet-mapping -->
    <servlet-mapping>
        <Servlet-name>Spring</Servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    <!--End Servlet-mapping -->
```

最后启动 Tomcat 服务器装载 DispatcherServlet，接着就可以进行基于 SpringMVC 框架的 web 项目开发和使用，但是 Spring Boot 开发 web 项目时，开发者只需导入 `spring-boot-starter-web` 场景启动器即可，根本无需再进行任何配置就能够使得 DispatherServlet 正常加载并使用，这是因为 **Spring Boot 在项目启动时已经将 DispatherServlet 自动配置到项目中**了，因此我们无需做多余的配置即可完成接口的开发。

不过呢，使用 Spring Boot 开发 web 应用时，有两个需要注意的配置项：

- **server.port** 表示启动后的端口号，默认是 8080
- **server.servlet.context-path** 项目路径，是构成 url 地址的一部分

如果想要修改这两个参数值，可以在 `application.properties` 配置文件中修改，示例如下：

```properties
#设置项目启动端口
server.port = 8888
#设置项目访问路径 2.1.0版本
#server.servlet.context-path = /shiyanlou/
```

这样的话，启动后的端口号就不再是 8080 而是 8888

### 3.静态资源处理

简单来说，在 B/S 架构中，静态资源一般指 Web 服务器对应目录中的资源文件，客户端发送请求到 Web 服务器后，Web 服务器（比如 Tomcat）直接从文件目录中获取文件并返回给客户端，客户端解析并渲染显示出来，比如 HTML、CSS、JavaScript、图片等文件，这些文件原样返回给客户端，并不会在 Web 服务器端有所改变，在进行 web 项目开发时，对于这些资源的处理也是十分重要的，这一小节我们将会讲解 Spring Boot 是如何处理这些文件的。

```java
@ConfigurationProperties(
    prefix = "spring.resources",
    ignoreUnknownFields = false
)
public class ResourceProperties {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};
```

通过查看源码我们可以看到，Spring Boot 默认存放静态资源文件的目录有四个，这些目录都在类路径下，它们分别是：

- **/META-INF/resources/**
- **/resources/**
- **/static/**
- **/public/**

也就是说，在进行 web 功能开发时，如果项目中存在静态资源文件，如 HTML、CSS、JavaScript、图片等文件，只要将这些文件放到以上四个文件目录即可正常访问到。接下来，我们新建四个目录并进行测试，类路径对应到项目路径中就是 **src/main/resources** ，新的目录结构如下：

src/main/resources
  ├── application.properties
  ├── META-INF
  │   └── resources
  ├── public
  ├── resources
  ├── static
  └── templates

为了验证，我们在分别在每个目录中新增一个静态资源文件，分别是 /META-INF/resources/test.html 、/resources/shiyanlou.png 、/static/test.css 、/public/test.js :

为 test.html 添加如下内容：

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>test</title>
  </head>
  <body>
    resource - html
  </body>
</html>
```

下载 shiyanlou.png 到public目录。

为 test.css 添加如下内容：

```css
function test() {
  console.log('resource - js');
}
```

接下来启动项目，启动成功通过浏览器地址栏访问,访问路径为：

- /test.html
- /test.css
- /test.js
- /shiyanlou.png

结果都是正确的，文件虽然在不同的路径中，但是四个路径都是默认的静态资源目录，因此都能够被访问到。当然，有的同学可能会说了，我就想要访问 shiyanlou 目录下的静态资源文件，这种情况该怎么办呢？其实，前面四个静态资源目录是 Spring Boot 默认设置的，如果想要更改，可以修改配置文件中的 `spring.resources.static-locations` 参数。比如，我们想要访问 shiyanlou 目录下的静态资源文件，首先需要在`src/main/resources`下新建 shiyanlou 目录，将静态资源复制到该目录中：

shiyanlou
  ├── shiyanlou.png
  ├── test.css
  └── test.html

我们复制了三个静态资源文件，test.js 依然放在 static 目录中，之后修改配置文件，添加如下设置：

```properties
spring.resources.static-locations=classpath:/shiyanlou/
```

重启后，在浏览器中再次分别访问这些静态资源，你会发现 shiyanlou 目录下的三个文件都能够正常访问，但是 test.js 访问时报错 404 了，因为配置文件中静态资源路径并没有包含 static 目录，shiyanlou 目录下也没有 test.js。如果想要正常访问到该文件，有两个办法，一是将 test.js 文件放到 shiyanlou 目录下，二是修改配置文件为:

```properties
spring.resources.static-locations=classpath:/shiyanlou/,classpath:/static/
```

`spring.resources.static-locations` 的参数值可以设置为一个数组。这样修改后，重启后又能够访问所有的静态资源文件了。

### 4.消息转换器 HttpMessageConverter

以往在使用 SpringMVC 框架开发项目时，大家应该都使用过 `@RequestBody`、`@ResponseBody` 注解进行请求实体的转换和响应结果的格式化输出，以普遍使用的 json 数据为例，这两个注解的作用分别可以将请求中的数据解析成 json 并绑定为实体对象以及将响应结果以 json 格式返回给请求发起者，但 Http 请求和响应是基于文本的，也就是说在 SpringMVC 内部维护了一套转换机制，也就是我们通常所说的“将 json 格式的请求信息转换为一个对象，将对象转换为 json 格式并输出为响应信息 ”，这些就是 HttpMessageConverter 的作用。

举一个简单的例子，我们定义一个实体类，并通过 `@RequestBody`、`@ResponseBody` 注解进行参数的读取和响应，代码如下：

com.itheng
​     ├── SpringBootSlyHelloworld01Application.java
​     ├── controller
​     │   ├── HelloController.java
​     │   └── TestController.java
​     ├── entity
​     │   └── SaleGoods.java
​     └── service
​         └── HelloService.java

SaleGoods.java 的代码内容如下：

```java
package com.itheng.entity;

/**
 * @Author PV-CZH mail:zilemontree@yeah.net
 * @description 实体类
 * @Date 2020/12/10 22:46
 * @title: SaleGoods
 * @projectName spring-boot-shiyanlou-projects
 */
public class SaleGoods {
    private Integer id;
    private String goodsName;
    private float weight;
    private int type;
    private Boolean onSale;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    public Boolean getOnSale() {
        return onSale;
    }
    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "SaleGoods{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", weight=" + weight +
                ", type=" + type +
                ", onSale=" + onSale +
                '}';
    }
}

```

TestController.java 控制器方法如下，拿到参数数值后进行简单的修改并将对象数据返回：

```java
package com.itheng.controller;

import com.itheng.entity.SaleGoods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author PV-CZH mail:zilemontree@yeah.net
 * @description TestController
 * @Date 2020/12/10 22:47
 * @title: TestController
 * @projectName spring-boot-shiyanlou-projects
 */
@Controller
public class TestController {
    @RequestMapping(value = "/get/httpmessageconverter", method = RequestMethod.GET)
    @ResponseBody
    public SaleGoods httpMessageConverterTest() {
        SaleGoods saleGoods = new SaleGoods();
        saleGoods.setGoodsName("华为手机");
        saleGoods.setId(1);
        saleGoods.setOnSale(true);
        saleGoods.setType(1);
        saleGoods.setWeight(300);
        return saleGoods;
    }
}
```

编码完成后重启项目，点击 WEB 服务，并在地址栏地址末尾添加 `/get/httpmessageconverter` 进行测试，结果如下：

JSONVIEWER插件显示结果

```json
{
"id": 1,
"goodsName": "华为手机",
"weight": 300.0,
"type": 1,
"onSale": true
}
```

添加 `@ResponseBody` 注解后，Spring Boot 会直接将对象转换为 json 格式并输出为响应信息，这是将对象作为相应数据的例子。 接下来我们再写一个案例，使用 `@RequestBody` 接收前端请求并将参数转换为后端定义的对象，在 `TestController` 类中添加的方法如下，请求方法为 POST，并使用 `@RequestBody` 注解将前端传输的参数直接转换为 SaleGoods 对象。

```java
    @RequestMapping(value = "/test/httpmessageconverter", method = RequestMethod.POST)
    @ResponseBody
    public SaleGoods httpMessageConverterTest2(@RequestBody SaleGoods saleGoods) {
        System.out.println(saleGoods.toString());
        saleGoods.setType(saleGoods.getType() + 1);
        saleGoods.setGoodsName("商品名：" + saleGoods.getGoodsName());
        return saleGoods;
    }
```

由于是 POST 请求，因此没有直接使用浏览器访问，而是在 static 中新增了 `api-test.html` 页面进行模拟请求,页面当中使用的是 `applicaiton/json` 格式进行 ajax 请求，`resources/static/api-test.html` 代码如下：

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>lou.SpringBoot | 请求测试</title>
  </head>
  <body class="hold-transition login-page">
    <div style="width:720px;margin:7% auto">
      <div class="content">
        <div class="container-fluid">
          <div class="row">
            <div class="col-lg-6">
              <div class="card">
                <div class="card-header">
                  <h5 class="m-0">接口测试</h5>
                </div>
                <div class="card-body">
                  <input id="id" type="text" placeholder="请输入id字段" />
                  <input
                    id="goodsName"
                    type="text"
                    placeholder="请输入goodsName字段"
                  />
                  <input
                    id="weight"
                    type="number"
                    placeholder="请输入weight字段"
                  />
                  <input id="type" type="number" placeholder="请输入type字段" />
                  <input
                    id="onSale"
                    type="number"
                    placeholder="请输入onSale字段(0或1)"
                  />
                  <h6 class="card-title">接口返回数据如下：</h6>
                  <p class="card-text" id="result2"></p>
                  <a
                    href="#"
                    class="btn btn-primary"
                    onclick="requestPostTest()"
                    >发送Post请求</a
                  >
                </div>
              </div>
              <br />
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- jQuery -->
    <script src="https://cdn.staticfile.org/jquery/1.12.0/jquery.min.js"></script>
    <script type="text/javascript">
      function requestPostTest() {
        var onSale = true;
        var id = $('#id').val();
        var weight = $('#weight').val();
        var type = $('#type').val();
        var goodsName = $('#goodsName').val();
        var onSaleValue = $('#onSale').val();
        if (onSaleValue !== 1) {
          onSale = false;
        }
        var data = {
          id: id,
          goodsName: goodsName,
          weight: weight,
          type: type,
          onSale: onSale,
        };
        $.ajax({
          type: 'POST', //方法类型
          dataType: 'json', //预期服务器返回的数据类型
          url: '/test/httpmessageconverter',
          contentType: 'application/json; charset=utf-8',
          data: JSON.stringify(data),
          success: function (result) {
            $('#result2').html(JSON.stringify(result));
          },
          error: function () {
            $('#result2').html('接口异常，请联系管理员！');
          },
        });
      }
    </script>
  </body>
</html>
```

重启项目，在地址栏访问 `/api-test.html` 页面，并在页面输入框中输入对应的数据，点击请求按钮，最终获得结果如下:

```json
//通过页面发送POST请求
{"id":1,"goodsName":"商品名：czh","weight":120,"type":1,"onSale":false}
```

前端 ajax 传输的数据是 5 个字段，到达后端后直接转换为 SaleGoods 对象。由于消息转换器的存在，对象数据的读取不仅简单而且完全正确，响应时也不用自行封装工具类，使得开发过程变得更加灵活和高效。