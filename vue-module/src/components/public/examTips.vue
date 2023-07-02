<template>
  <div class="container">
    <el-header style="text-align: left; font-size: 30px">
      <span>考试报名提示</span>
    </el-header>
    <el-main>
      <template>
        <br />
        <el-container style="border: 1px solid #eee">
          <el-header style="text-align: center; font-size: 12px">
            <h1>
              <span>2023年上半年考试时间</span>
            </h1>
          </el-header>
          <el-main>
            <p>
              笔试：6-17 下午
            </p>
            <p>
              口语：5-21
            </p>
          </el-main>
          <el-header style="text-align: center; font-size: 12px">
            <h1>
              <span>考试报名提示</span>
            </h1>
          </el-header>
          <el-main>
            <p>
              1. 报名未开始前，考生可进行注册及学籍信息验证操作，个人信息错误请及时与所在学校有关部门联系更正。具体报名时间以各学校通知为准。
            </p>
            <p>
              2. 请在报名完成后24小时内完成缴费，并再次登录系统确认是否报名成功，如有疑问可拨打客服电话进行咨询。
            </p>
            <p>
              3. 关于调整全国大学英语六级口语考试成绩报告方式的通知。
            </p>
          </el-main>
        </el-container>
      </template>
    </el-main>
    <el-footer style="text-align: center">
      <template>
        <el-button style="margin-top: 12px" @click="finishReading"
        >完成阅读</el-button
        >
      </template>
    </el-footer>
  </div>
</template>

<script>
export default {
  name: "takeinExam",
  methods: {
    finishReading: function () {
      this.$router.push({ name: "homepage" });
    },
  },
};
</script>
