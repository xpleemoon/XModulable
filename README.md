## XModulable

组件化的套路通常是：

  - 组件之间互不依赖、相互隔离
  - app壳将组件注册到路由层
  - 上层通过路由层查找组件，通过路组件暴露的服务实现通信交互

本例中的组件化：
  - 路由采用[ARouter](https://github.com/alibaba/ARouter)实现
  - XModulable SDK负责组件的注册和查找，这里的组件可视为组件服务的容器：
    - `@XModule`——组件声明
    - `@InjectXModule`——组件注入声明
    - `XModulable`——作为组件的注册、查找和依赖注入
  - 业务组件独立运行，只需要更改module.gradle对应的业务组件`isStandalone`为true即可

---

### Latest Version

SDK|XModulable-api|XModulable-compiler|XModulable-annotation
:---|:---:|:---:|:---:
最新版本|[ ![Download](https://api.bintray.com/packages/xpleemoon/maven/XModulable-api/images/download.svg) ](https://bintray.com/xpleemoon/maven/XModulable-api/_latestVersion)|[ ![Download](https://api.bintray.com/packages/xpleemoon/maven/XModulable-compiler/images/download.svg) ](https://bintray.com/xpleemoon/maven/XModulable-compiler/_latestVersion)|[ ![Download](https://api.bintray.com/packages/xpleemoon/maven/XModulable-annotation/images/download.svg) ](https://bintray.com/xpleemoon/maven/XModulable-annotation/_latestVersion)

### 使用方法

1. 添加依赖配置

  ```
  android {
      defaultConfig {
      	...
      	javaCompileOptions {
      	    annotationProcessorOptions {
      		      arguments = [ XModule : project.getName() ]
      	    }
      	}
      }
  }

  dependencies {
      // gradle3.0以上建议使用implementation(或者api) 'com.xpleemoon.xmodulable:XModulable-api:x.x.x'
      compile 'com.xpleemoon.xmodulable:XModulable-api:x.x.x'
      annotationProcessor 'com.xpleemoon.xmodulable:XModulable-compiler:x.x.x'
      ...
  }
  ```

2. 实现组件

  ```
  @XModule(name = "XX组件名")
  public class XXModule implements IModule{

  }
  ```

3. 初始化sdk

  ```
  if (isDebug) {
      XModulable.openDebug();
  }
  XModulable.init(this);
  ```

4. 获取组件

  组件获取有两种方式：依赖注入和手动查询获取。

  1. 依赖注入：

    ```
    public class TestActivity extends BaseActivity {
        @InjectXModule(name = "xxx")
        XXModule mXXModule;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            XModulable.inject(this);
        }
    }
    ```

  2. 手动获取：

    ```
    XModulable.getInstance().getModule("XX组件名")
    ```

5. 添加混淆规则

  ```
  -keep class * implements com.xpleemoon.xmodulable.api.template.XModuleLoader
  -keep class * implements com.xpleemoon.xmodulable.api.IModule
  -keep class **$$XModulableInjector { *; }
  ```
