# Singleton

- Singleton 패턴은 인스턴스를 불필요하게 생성하지 않고 오직 JVM내에서 한 개의 인스턴스만 생성하여 재사용을 위해 사용되는 디자인패턴이다.
- 멀티 스레드 환경에서 사용 시 Thread-safe 한 싱글톤을 작성해야한다.

## 고전적인 방식의 싱글톤 패턴

```java
public class Singleton {
    private static Singleton instance;
    private Singleton(){}
    public static Singleton getInstance() { 
        if(instance == null) { // 1번 : 쓰레드가 동시 접근시 문제 
            instance = new Singleton(); // 2번 : 쓰레드가 동시 접근시 인스턴스 여러번 생성
        } 
        return instance; 
    } 
}
```

- 위에서는 두개의 쓰레드가 접근 시 new Instance 를 두번 할 수 있는 문제가 있음

## synchronized 를 이용한 Singleton 패턴

```java
public class Singleton {
    private static Singleton instance;

    private Singleton(){}
    public static synchronized Singleton getInstance() {
        if(instance == null) { // 1번
            instance = new Singleton(); // 2번
        }
        return instance;
    }
}
```

- lock 을 사용한 스레드 동시 접근 제어
- synchronized 의 경우 인스턴스를 반환 할 때마다 Thread 동기화 때문에 불필요하게 lock이 걸려 이용 낭비가 크다.
- 초기에만 2개 생성에 문제가 있지 이후에는 없기 때문에 불필요한 lock 이 될 수 있음

## DCL(Double-Checked-Locking) Singleton 패턴

```java
public class Singleton {

    private static Singleton instance;
    private Singleton() {}

    public static Singleton getInstance() { 
        if(instance == null) { 
            synchronized (Singleton.class) { 
                if(instance == null) { 
                    instance = new Singleton(); 
                } 
            } 
        } 
    return instance; 
    }
}
```

- 인스턴스를 생서하는 경우에만 부분적으로 synchronized 처리
- 생성과 획득을 분리한 획기적인 방법
- 인스턴스가 생되지 않는 경우 lock을 잡고 생성함.
- 하지만. 이때도 문제가 될 수 있다. 컴파일러가 재배치문제를 일으키는데, 이는 인스턴스가 생성되지 않았지만 생성된 것으로 인식할 여지가 있다.
    - instance 에 some_space가 할당되는 경우
    - some_space = allocate space for Singleton object
    - 즉 공간할당이 이루어지고 아직 create a real object in some_space 가 되지 않은 경우! 에도 인스턴스가 생성된 걸로 처리될 여지가 있음

## volatile를 이용한 개선된 DCL (Double-checked-Locking) Singleton 패턴 (jdk 1.5 이상)

```java
public class Singleton { 
    private volatile static Singleton instance; // 👈
    private Singleton(){}
    public static Singleton getInstance() { 
        if(instance == null) { 
            synchronized (Singleton.class) { // 👈
                if(instance == null) { 
                    instance = new Singleton(); 
                } 
            } 
        } 
        return instance;
    } 
}
```

- volatile (휘발성의, 변덕스러운, 불안한)
  - 사용 시 instance는 CPU 캐시에서 변수를 참조하지 않고 메인 메모리에서 변수를 참조하게됨.
  - 그래서 위에서 발생한 reorder 문제가 발생하지 않음.
  - 안정적인 방법으로 알려져있다.

## static 초기화를 이용한 Singleon 패턴

```java
public class Singleton { 
    private static Singleton instance = new Singleton(); // static 초기화시 바로 할당 
    private Singleton(){} 
    public static Singleton getInstance() { 
        return instance; 
    } 
}
```

```java
public class Singleton { 
    private static Singleton instance; 
    static { 
        instance = new Singleton(); 
    } 
    private Singleton(){} 
    public static synchronized Singleton getInstance() { 
        return instance; 
    } 
}
```

- Thread-safe
- 소스 간결
- 성능 good.
- Class 로딩 시점에 초기화되 하나의 인스턴스만 생성을 보장
- 문제가 있긴함 : 실제로 사용할지 않할지 모르는 인스턴스를 미리 생성해도 되는가의 문제. (낭비라는 의견이 있음)

## LazyHolder Singleton 패턴

```java
public class Singleton { 
    private Singleton(){} 
    public static Singleton getInstance() { 
        return LazyHolder.INSTANCE; 
    } 
    private static class LazyHolder { 
        private static final Singleton INSTANCE = new Singleton(); 
    } 
}
```

- 가장 최적의 방법이라 평가되는 코드
- JAVA 버전 무관
- 성능 good.
- static 영역에 초기화 하지만 필요한 시점까지 초기화를 미루는 방식임.
- `LazyHolder`의 변수가 없어서 Singleton 클래스 로딩 시 `LazyHolder 클래스`를 초기화 하지 않음.
- Singleton 클래스의 `getInstance()` 메서드에서 `LazyHolder.INSTANCE` 를 참조하는 순간 Class 가 로딩되며 초기화 진행된다.
- <u>Class 를 로딩하고 초기화하는 시점은 thread-safe 를 보장</u>하기 때문에 `volatile` 이나 `synchronized` 같은 키워드가 없어도 thread-safe 하고. 성능도 보장. 아주 훌륭하다.

# Volatile의 동기화

- Class의 멤버변수는 heap 메모리에 존재하기 때문에 thread가 공유하여 접근할 수 있음
- 이때 각 thread는 속도 향상을 위해 main memory에 접근해서 값을 가져가는 것이 아니라, cache 에서 변수값을 읽어감
- 쓰기도 마찬가지로 cache 값을 이용하는데. 어떤 시점에 변경된 cache 값이 main memory에 업데이트 된다.
- 따라서 멀티 스레드 환경에서 해당 변수의 값을 읽거나 쓰면 자신의 cache에서 값을 읽고 써서 어떤 시점에 변경된 값을 main memeory에 갱신하기 때문에 실제원하는 값이랑 다른 문제가 발생할 수 있습니다.
- Thread-1 이 해당 변수를 바꿨는데 Thread-2 가 변경전 값을 cache 하여 이 값을 기준으로 연산하여 변수의 값을 변경하게 되면 추후 실제 main memory 에서는 엉뚱한 값이 있게 되는 것.
- 해당 문제를 막기 위해 `Volatile` 사용.
  - 접근 가능한 변수를 cache를 통해 사용하는 것이 아닌 thread 가 직접 main memory 에 접근해서 읽고 쓴다.

단점도 있다.

- Cache 사용이 아닌 main memory 직접 접근이기 때문에 더 비싼 cost
- Volatile 변수는 읽기/쓰기가 JVM에 의해 reordering 되지 않음
  - volatile 읽기/쓰기 이후의 연산들은 반드시 읽기/쓰기 이후에 이루어짐
  - 따라서 필요에 따라 성능상 이유로 JVM의 instruction reorder 동작을 못하도록 막아 성능면에서 손해
- volatile 변수는 read 시 항상 최신 값을 반환.
  - 단, 여러 쓰레드가 동시 읽기,쓰기를 하면 쓰기 시점을 알 수 없기에 단독 사용시 thread 안정성 X

# kotlin ☂️

```kotlin
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Product::class)], version = 1)
abstract class ProductRoomDatabase : RoomDatabase() {

    @Volatile // 👈
    abstract fun productDao(): ProductDao

    companion object {

        private var INSTANCE: ProductRoomDatabase? = null

        internal fun getDatabase(context: Context): ProductRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(ProductRoomDatabase::class.java) { // 👈
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<ProductRoomDatabase>(
                            context.applicationContext,
                            ProductRoomDatabase::class.java,
                            "product_database"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
```

[reference](https://javaplant.tistory.com/21)
[reference2](https://tourspace.tistory.com/163)
