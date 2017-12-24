# Component Sample

组件化的套路通常是：

  - 组件之间互不依赖、相互隔离
  - app壳将组件注册到路由层
  - 上层通过路由层查找组件，通过路组件暴露的服务实现通信交互

> - 路由采用[ARouter](https://github.com/alibaba/ARouter)实现
> - component sdk：`@Component`注解作为组件声明、`ComponentManager`作为组件的注册和查找

## 使用方法

1. 添加依赖配置

  ```
  android {
      defaultConfig {
      	...
      	javaCompileOptions {
      	    annotationProcessorOptions {
      		      arguments = [ componentName : project.getName() ]
      	    }
      	}
      }
  }

  dependencies {
      // gradle3.0以上建议使用implementation(或者api) 'com.xpleemoon.component:component-api:0.1.3'
      compile 'com.xpleemoon.component:component-api:0.1.3'
      annotationProcessor 'com.xpleemoon.component:component-compiler:0.1.3'
      ...
  }
  ```

2. 实现组件

  ```
  @Component(name = "XX组件名")
  public class XXComponent implements IComponent{

  }
  ```

3. 初始化sdk

  ```
  if (isDebug) {
      ComponentManager.openDebuggable();
  }
  ComponentManager.init(this);
  ```

4. 获取组件

  组件获取有两种方式：依赖注入和手动查询获取。

  1. 依赖注入：

    ```
    public class TestActivity extends BaseActivity {
        @InjectComponent(name = "xxx")
        XXComponent mXXComponent;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            try {
                ComponentManager.inject(this);
            } catch (UnknownComponentException e) {
                e.printStackTrace();
            }
        }
    }
    ```

  2. 手动获取：

    ```
    ComponentManager.getInstance().getComponent("XX组件名")
    ```

5. 添加混淆规则

  ```
  -keep class * implements com.xpleemoon.component.api.template.IComponentLoader
  -keep class * implements com.xpleemoon.component.api.IComponent
  -keep class **$$ComponentInjector { *; }
  ```
