# Bean的作用域和线程安全问题

**单例池：**ConcurrentHashMap

* key：beanName
* value：Object

Spring 的Bean作用域（scope）类型：

1. singleton:单例，默认作用域，非懒加载的单例Bean在Spring启动时创建好。

2. prototype:原型，每次创建一个新对象。

3. request:请求，每次Http请求创建一个新对象，适用于WebApplicationContext环境下。

4. session:会话，同一个会话共享一个实例，不同会话使用不用的实例。

5. global-session:全局会话，所有会话共享一个实例。


**原型Bean：** 对于原型Bean,每次创建一个新对象，也就是线程之间并不存在Bean共享，自然是不会有线程安全的问题。

**单例Bean：** 对于单例Bean,所有线程都共享一个单例实例Bean,因此是存在资源的竞争。如果单例Bean,是一个无状态Bean，也就是线程中的操作不会对Bean的成员执行查询以外的操作，那么这个单例Bean是线程安全的。比如Spring mvc 的 Controller、Service、Dao等，这些Bean大多是无状态的，只关注于方法本身。

**有状态Bean(Stateful Bean)：** 就是有实例变量的对象，可以保存数据，是非线程安全的。每个用户有自己特有的一个实例，在用户的生存期内，bean保持了用户的信息，即“有状态”；一旦用户灭亡（调用结束或实例结束），bean的生命期也告结束。即每个用户最初都会得到一个初始的bean。

**无状态Bean(Stateless Bean)：**就是没有实例变量的对象，不能保存数据，是不变类，是线程安全的。bean一旦实例化就被加进会话池中，各个用户都可以共用。即使用户已经消亡，bean 的生命期也不一定结束，它可能依然存在于会话池中，供其他用户调用。由于没有特定的用户，那么也就不能保持某一用户的状态，所以叫无状态bean。但无状态会话bean 并非没有状态，如果它有自己的属性（变量），那么这些变量就会受到所有调用它的用户的影响，这是在实际应用中必须注意的。


**对于有状态的bean**，Spring官方提供的bean，一般提供了通过**ThreadLocal**去解决线程安全的方法，比如RequestContextHolder、TransactionSynchronizationManager、LocaleContextHolder等。

对于有状态的bean，Spring官方提供的bean，一般提供了通过ThreadLocal去解决线程安全的方法，比如RequestContextHolder、TransactionSynchronizationManager、LocaleContextHolder等。

**使用ThreadLocal的好处**
使得多线程场景下，多个线程对这个单例Bean的成员变量并不存在资源的竞争，因为ThreadLocal为每个线程保存线程私有的数据。这是一种以空间换时间的方式。

当然也可以通过加锁的方法来解决线程安全，这种以时间换空间的场景在高并发场景下显然是不实际的。