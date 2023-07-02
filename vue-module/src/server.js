const Koa = require('koa');
const Router = require('koa-router');
const mysql = require('mysql2/promise');

const app = new Koa();
const router = new Router();
router.post('/api/submit', async (ctx) => {
    try {
        console.log(ctx.request.body);
        const { examId, answers, userId } = ctx.request.body;

        // 连接到MySQL数据库
        const connection = await mysql.createConnection({
        host: 'localhost', // 修改为你的数据库主机名
        user: 'root', // 修改为你的数据库用户名
        password: 'Top50761', // 修改为你的数据库密码
        database: 'test-sql' // 修改为你的数据库名称
      });
      if (typeof answers[1] === "string") {
        console.log("变量是字符串类型");
      } else {
        console.log("变量不是字符串类型");
      }
      // 将数据保存到数据库中，这里使用示例代码，你可以根据数据库表结构进行相应的修改
      await connection.execute('INSERT INTO `a_stuanswer` VALUES (?,?,?,?,?)',[userId,examId,answers[1],answers[2],answers[3]]);
  
      connection.end(); // 关闭数据库连接
  
      ctx.body = { message: 'Exam submitted successfully' };
    } catch (error) {
      console.error(error);
      ctx.status = 500;
      ctx.body = { error: 'Internal server error' };
    }
  });
router.get('/api/questions/:examId', async (ctx) => {
    try {
        const examId = ctx.params.examId;

        console.log('Received examId:', examId);

        // 连接到MySQL数据库
        const connection = await mysql.createConnection({
        host: 'localhost', // 修改为你的数据库主机名
        user: 'root', // 修改为你的数据库用户名
        password: 'Top50761', // 修改为你的数据库密码
        database: 'test-sql' // 修改为你的数据库名称
    });

    // 执行查询语句获取考题内容
    const rows = await connection.execute('SELECT * FROM a_questions WHERE exam_entry_id = ?', [examId]);

    console.log(rows.length);

    connection.end(); // 关闭数据库连接

    ctx.body = { data: rows }; // 将查询结果作为JSON响应发送给前端
    } catch (error) {
    console.error(error);
    ctx.status = 500;
    ctx.body = { error: 'Internal server error' };
    }
});
router.get('/api/teacher/getexam', async (ctx) => {
    try {
        // 连接到MySQL数据库
        const connection = await mysql.createConnection({
        host: 'localhost', // 修改为你的数据库主机名
        user: 'root', // 修改为你的数据库用户名
        password: 'Top50761', // 修改为你的数据库密码
        database: 'test-sql' // 修改为你的数据库名称
    });

    // 执行查询语句获取考题内容
    const rows = await connection.execute('SELECT * FROM exam_entry');

    console.log(rows);

    connection.end(); // 关闭数据库连接

    ctx.body = { data: rows }; // 将查询结果作为JSON响应发送给前端
    } catch (error) {
    console.error(error);
    ctx.status = 500;
    ctx.body = { error: 'Internal server error' };
    }
});
app.use(async (ctx, next) => {
    ctx.set('Access-Control-Allow-Origin', 'http://localhost:8081');
    ctx.set('Access-Control-Allow-Methods', 'GET, POST');
    ctx.set('Access-Control-Allow-Headers', 'Content-Type');
    ctx.set('Access-Control-Allow-Credentials', 'true'); // 允许凭证
    await next();
});
const bodyParser = require('koa-bodyparser');

app.use(bodyParser()); // 使用中间件解析请求体
app.use(router.routes());
app.use(router.allowedMethods());

app.listen(3000, () => {
  console.log('Server is running on port 3000');
});