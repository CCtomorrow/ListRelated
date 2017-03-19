### 依赖
root
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

module
```
dependencies {
	compile 'com.github.qingyongai:ListRelated:v1.0.0@aar'
	// 如果需要使用Fragment和RecyclerView，请自行添加依赖
	}
```
