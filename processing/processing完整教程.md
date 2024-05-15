**概述**：processing在我眼里就是libgdx的高度封装，如果各位会libgdx，学processing应该可以说是无师自通，当然processing是java语言那边的。

**processing是什么？**

官网是这样解释的：Processing 是一本灵活的软件速写本，也是一种用于学习如何编码的语言。自 2001 年以来，Processing 一直在促进视觉艺术中的软件素养和技术中的视觉素养。有数以万计的学生、艺术家、设计师、研究人员和业余爱好者使用处理进行学习和原型设计。

**其实我觉得就不能算是语言，顶多是java的libgdx的高度封装**

**那为什么要学processing？**

对于我来说，java找不到工作了，准备躺平了，正巧爱好就是游戏，看了一眼processing，便迷住了。

# 第一步：下载processing

官网：[欢迎来到 Processing！/ Processing.org](https://processing.org/)

![img](https://img-blog.csdnimg.cn/direct/f97b0ce3222b4a7bb516215c3e2b4bed.png)

点击下载最新版即可。

# 第二步：启动processing

![img](https://img-blog.csdnimg.cn/direct/d89d81e0d7af48efa9e779479a67eb37.png)


点击exe文件即可启动processing

# 第三步：开始玩转processing

画个窗口

```
size(680,320);
```

![img](https://img-blog.csdnimg.cn/direct/74c91ea01d95443cbeceb8c0dfb306d1.png)

## 输出"Hello World"

```
size(680,320);
background(0);
textSize(64);
textAlign(CENTER);
text("Hello World", 200, 200);
```

![img](https://img-blog.csdnimg.cn/direct/2601a1b4b7ad4991b3eff12066526ffc.png)

## 规范格式

正如我说的，这个是libgdx的高度封装，所以会有很多libgdx的内置函数。一般我们要按照libgdx格式规范

```
void setup() {
  size(680, 320);
  background(0);
}

void draw() {
  textSize(64);
  textAlign(CENTER);
  text("Hello World", 200, 200);
}
```

![img](https://img-blog.csdnimg.cn/direct/fac2fa367a91458cb675cab4f6fa1603.png)

现在，我来讲解一下这个格式，setup函数是只执行一次，通常用于初始化，而draw函数则是无限次执行，里面有一个while循环，只要程序没有中断，就会不断的draw。

## 移动小球案例

正如我之前说的，程序会不断的draw，在draw过程中，改变小球的位置，就能实现移动

```
float circleX,circleY;

void setup() {
  circleX=0;
  circleY=0;
  size(680, 320);
  background(0);
}

void draw() {
  circle(circleX, circleY, 20);
  circleX+=5;
  circleY+=5;
}
```


正如我说的会不断的画⚪，但是会有很多的小球，解决这个问题也很简单，就在画的前面设置一下背景，用背景覆盖一下原先的小球即可

```
float circleX, circleY;

void setup() {
  circleX=0;
  circleY=0;
  size(680, 320);
  background(0);
}

void draw() {
  background(0);
  circle(circleX, circleY, 20);
  circleX+=5;
  circleY+=5;
}
```

惯例写法，规范类
可以将class全部定义到一个文件里面，但是这样会导致混乱。就像下面一样

![img](https://img-blog.csdnimg.cn/direct/d766e07382264bfcb5e1d79ec0be59c7.png)

一般来说，将不同的类，不同的事物放到不同的文件上，这样会更美观

点击上面那个三角形

![img](https://img-blog.csdnimg.cn/direct/2c7a339e5f4b4036ab50c34e5ddd0959.png)

![img](https://img-blog.csdnimg.cn/direct/c6de91ed247348c7b44d6ac955bd9563.png)



然后就可以把⚪这个对象，和我主类区开

主类写：

```
BoxA boxA;
void setup() {
  boxA=new BoxA(0,0,20);
  size(680, 320);
  background(0);
}

void draw() {
  background(0);
  boxA.show();
  boxA.move(5,5);
}
```

![img](https://img-blog.csdnimg.cn/direct/e1222691878e45f5b6233b064386d08d.png)

⚪类写：

```
class BoxA{
  float circleY=0;
  float circleX=0;
  float circleR=0;
  public BoxA(float x,float y,float r){
    this.circleY=y;
    this.circleX=x;
    this.circleR=r;
  }
  public void show(){
     circle(circleX,circleY,circleR);
  }
  public void move(float xspeed,float yspeed){
    circleY+=yspeed;
    circleX+=xspeed;
  }
}
```

![img](https://img-blog.csdnimg.cn/direct/f7580673e76147baae504905f50302d8.png)

运行效果和之前一模一样

# 第四步：认识颜色

在processing有灰度和RGB颜色区分

灰度：就是0到255，由暗转明

RGB：则是red、green、blue三种颜色，对应的参数范围也是从0到255

颜色根据位置变动的小球案例
只要在其show方法里面画小球的前面加个填充RGB，即可

![img](https://img-blog.csdnimg.cn/direct/fb9e1e5d2dfd4d2ea86804f86a93283e.png)

第五步：学会打印
有时候需要调试bug，这个时候就要学会打印输出到控制台

```
 println(boxA.circleX);
```

就单纯的println即可，数字就会打印到控制台上。

![img](https://img-blog.csdnimg.cn/direct/8404a00c00de42aabc292e871ce7ef26.png)