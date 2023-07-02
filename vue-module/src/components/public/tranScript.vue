<template>
  <div class="container">
    <el-main>
      <template>
        <br />
        <el-container style="border: 1px solid #eee">
          <el-header style="text-align: center; font-size: 12px">
            <h1>
              <span>成绩报告单相关事项</span>
            </h1>
          </el-header>
          <el-main>
            <p>
              1. 自2021年上半年考试起，CET将免费提供电子成绩报告单。在成绩发布10个工作日后，考生登录中国教育考 试网（www.neea.edu.cn）查看并下载电子成绩报告单，电子成绩报告单与纸质成绩报告单同等效力。
            </p>
            <p>
              2. 考生在报名期间或成绩发布后规定时间内登录CET报名网站（cet-bm.neea.edu.cn），自助选择是否需要纸质成绩报告单。选择纸质的考生，应按考点规定时间及地点免费领取，超过半年未领取的，视为自动放弃，不再补发。
            </p>
            <p>
              3. 目前，中国教育考试网（www.neea.edu.cn）已开通2005年6月及以后历次考试成绩报告单查询服务，考生可登 录查询，并可根据实际情况自助办理纸质成绩证明。
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
  name: "tranScript",
  methods: {
    finishReading: function () {
      this.$router.push({ name: "homepage" });
    },
  },
};
</script>
