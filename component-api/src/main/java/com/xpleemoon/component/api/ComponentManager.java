package com.xpleemoon.component.api;


import android.content.Context;
import android.content.pm.PackageManager;

import com.xpleemoon.component.api.template.IComponentLoader;
import com.xpleemoon.component.api.utils.CacheUtils;
import com.xpleemoon.component.api.utils.ClassUtils;
import com.xpleemoon.component.api.utils.PackageUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * 业务组件管理类
 * <ul>
 * <li>上层通过{@link #init(Context)}实现{@link IComponent 组件}的注册</li>
 * <li>使用前确保先进行{@link #init(Context) 初始化}</li>
 * <li>暴露的{@link #getComponent(String)}用于获取已注册的{@link IComponent 组件}</li>
 * </ul>
 *
 * @author xpleemoon
 */
public class ComponentManager {
    private static boolean sDebuggable = false;
    private static Context sCtx;
    private static volatile ComponentManager sInstance;
    private final ComponentOptions mOptions;

    private ComponentManager() {
        this.mOptions = new ComponentOptions.Builder().build();
    }

    public static ComponentManager getInstance() {
        if (sInstance == null) {
            synchronized (ComponentManager.class) {
                if (sInstance == null) {
                    sInstance = new ComponentManager();
                }
            }
        }
        return sInstance;
    }

    public static boolean debuggable() {
        return sDebuggable;
    }

    public static void openDebuggable() {
        sDebuggable = true;
    }

    public static void closeDebuggable() {
        sDebuggable = false;
    }

    /**
     * 初始化组件服务管理类，在使用该类时确保先进行初始化
     */
    public static synchronized void init(Context context) {
        sCtx = context.getApplicationContext();
        Set<String> loaderSet = null;
        // 调试模式或者是新版本，会有新的类生成，因此需要重新加载，
        if (debuggable() || PackageUtils.isNewVersion(context)) {
            try {
                // 扫面由ComponentProcessor生成的组件加载器
                loaderSet = ClassUtils.getFileNameByPackageName(context, Constants.PACKAGE_OF_GENERATE);
                CacheUtils.updateComponentLoaderSet(context, loaderSet);
                PackageUtils.updateVersion(context);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            loaderSet = CacheUtils.getComponentLoaderSet(context);
        }

        if (loaderSet != null) {
            String prefixOfLoader = new StringBuilder()
                    .append(Constants.PACKAGE_OF_GENERATE)
                    .append(".")
                    .append(Constants.SDK_NAME)
                    .append(Constants.SEPARATOR_OF_CLASS_NAME)
                    .append(Constants.CLASS_OF_LOADER)
                    .toString();
            for (String className : loaderSet) {
                if (className.startsWith(prefixOfLoader)) {
                    try {
                        Class<IComponentLoader> componentLoaderClass = (Class<IComponentLoader>) Class.forName(className);
                        IComponentLoader componentLoader = componentLoaderClass.getConstructor().newInstance();
                        componentLoader.loadInto(getInstance().mOptions);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void check() {
        if (sCtx == null) {
            throw new IllegalStateException("ComponentManager未初始化");
        }
    }

    /**
     * @throws IllegalAccessException 组件{@code name}未注册或不存在
     * @see ComponentOptions#getComponent(String)
     */
    public IComponent getComponent(String name) throws IllegalAccessException {
        check();

        IComponent component = mOptions.getComponent(name);
        if (component == null) {
            throw new IllegalAccessException(String.format("组件%1s未注册或不存在", name));
        }
        return component;
    }
}
