<template>
  <div class="container">
    <el-header style="text-align: left; font-size: 30px">
      <span>常见问题</span>
    </el-header>
    <el-main>
      <template>
        <br />
        <el-container style="border: 1px solid #eee">
          <el-header style="text-align: center; font-size: 5px">
            <h1>
              <span>一、CET（含口语CET-SET）网上报名流程</span>
            </h1>
          </el-header>
          <el-main>
            <p>
              1. 考生登录报名网站点击“进入报名”按钮后进行登录，如首次登录请先进行注册。
            </p>
            <p>
              2. 登录成功后，考生输入姓名、身份证件号验证个人、学籍及可报科目信息
            </p>
            <p>
              3. 考生确认个人、学籍及可报科目信息无误后，点击确认进入到科目报考阶段。如对以上信息有异议，请联系学校有关部门。
            </p>
            <p>
              4. 考生进行笔试科目的报考与缴费，笔试科目考点为考生所在学校，无须选择。
            </p>
            <p>
              5. 完成笔试科目报考后，考生可以进行口试科目的报考，缴费时需先支付完成笔试科目，再支付口试科目。
            </p>
            <p>
              6. 考生确认自己所选科目后，须在24小时之内完成缴费，否则报考失效。
            </p>
            <p>
              7. 报考完成后，考生可返回查看报考情况。
            </p>
            <p>
              8. 请报考口语考试的考生按各省级承办机构通知时间登录网站打印准考证。
            </p>
            <p>
              9. 请报考笔试考生按规定时间登录网站打印准考证或按学校要求领取准考证。
            </p>
          </el-main>
          <el-header style="text-align: center; font-size: 5px">
            <h1>
              <span>二、CET笔试及口试报考资格</span>
            </h1>
          </el-header>
          <el-main>
            <p>
              1. 报考笔试各科目须符合考生所在学校规定的报考资格，报考CET6考生CET4成绩须在425分及以上。
            </p>
            <p>
              2. 报考口试各科目，须完成当次对应级别笔试科目的报考及缴费。例如，报考2023年上半年CET-SET4，须完成2023年上半年CET4的报考及缴费。
            </p>
          </el-main>
          <el-header style="text-align: center; font-size: 5px">
            <h1>
              <span>三、有关资格验证的问题</span>
            </h1>
          </el-header>
          <el-main>
            <p>
              1. 资格验证无法通过
            </p>
            <p>
              （1）首先请检查姓名、证件类型、证件号码是否正确。
            </p>
            <p>
              （2）确保正确后仍无法验证通过，请联系您所在学校的相关部门。
            </p>
            <p>
              2. 个人信息、学籍信息及报考科目不正确
            </p>
            <p>
              学籍验证通过后，如发现照片、院系、所属学校等信息不正确时请及时联系您所在学校的相关部门进行更改。
            </p>
          </el-main>
          <el-header style="text-align: center; font-size: 5px">
            <h1>
              <span>四、用户注册相关问题</span>
            </h1>
          </el-header>
          <el-main>
            <p>
              1. 用户注册信息中姓名、身份证号与学籍信息不一致导致无法进行报考。
            </p>
            <p>
              （1）如果是学籍信息错误，请联系您所在学校相关部门进行修改。
            </p>
            <p>
              （2）如果是注册信息有误，请登录通行证网站自行修改。
            </p>
            <p>
              2. 姓名中有生僻字怎么注册
            </p>
            <p>
              请按照学籍信息中姓名中生僻字的方式进行注册。
            </p>
            <p>
              3. 注册页面没有“提交”按钮或验证码不显示怎么办？
            </p>
            <p>
              建议您使用火狐浏览器、谷歌浏览器、IE9+、360浏览器（选择极速模式）进行操作。
            </p>
            <p>
              4. 登录账号后页面只显示通行证信息，如何报名？
            </p>
            <p>
              请您再次输入CET报名网址，点击“进入报名”。
            </p>
            <p>
              5. 密码忘记了怎么办？
            </p>
            <p>
              请您点击“忘记密码”，通过原注册邮箱或手机号进行密码重置。
            </p>
            <p>
              6. 注册邮箱忘记了怎么办？
            </p>
            <p>
              请您点击“找回账号”，通过输入个人信息进行找回。
            </p>
            <p>
              7. 怎样更改注册邮箱？
            </p>
            <p>
              请考生自行登录通行证网站，点击“修改资料”进行更改邮箱。
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
  name: "commonQuestions",
  methods: {
    finishReading: function () {
      this.$router.push({ name: "homepage" });
    },
  },
};
</script>
