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

app.use(router.routes());


app.listen(3000, () => {
  console.log('Server is running on port 3000');
});