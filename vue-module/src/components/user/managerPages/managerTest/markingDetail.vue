<head>
<meta charset="UTF-8">
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<!-- 引入样式 -->
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<!-- 引入组件库 -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<template>
  <div>
    <div style="overflow: hidden;">
      <div style="float: left; width: 50%;">
        <div style="height: 1600px;overflow-y: scroll;">
          <p style="font-family: SimSun,Arial, sans-serif; font-size: 16px; font-weight: bold;color: #090000; text-align: justify;">
            翻译部分
          </p >
          <div>
            <textarea v-model="string_answers2" style="width: 100%; height: 200px;"></textarea>
          </div>
          <p style="font-family: SimSun,Arial, sans-serif; font-size: 16px; font-weight: bold;color: #090000; text-align: justify;">
            翻译打分
          </p >
          <div>
            <textarea v-model="score2" style="width: 100%; height: 50px;"></textarea>
          </div>
        </div>
      </div>
      <div style="float: right; width: 50%">
        <p style="font-family: SimSun,Arial, sans-serif; font-size: 16px; font-weight: bold;color: #090000; text-align: justify;">
          写作部分
        </p >
        <div>
          <textarea v-model="string_answers3" style="width: 100%; height: 200px;"></textarea>
        </div>
        <p style="font-family: SimSun,Arial, sans-serif; font-size: 16px; font-weight: bold;color: #090000; text-align: justify;">
          写作打分
        </p >
        <div>
          <textarea v-model="score3" style="width: 100%; height: 50px;"></textarea>
        </div>
        <el-button style="margin-top: 12px" @click="finishMark">阅卷结束</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import axios from 'axios'
export default {
  inject: ['reload'],
  name: 'markingDetail',
  data () {
    return {
      examDetailId: '',
      userId: '',
      string_answers2: 'null',
      string_answers3: 'null',
      score2: 0,
      score3: 0
    }
  },
  computed: {
    ...mapState({
      print: (state) => state.print.all,
      userId: (state) => state.userId.all
    })
  },
  mounted: function () {
    this.examDetailId = this.$route.params.examDetailId
    this.userId = this.$route.params.userId
    var thatt = this
    axios({
      headers: { Authorization: thatt.print.Authorization },
      method: 'get',
      url: '/api/studentAnswer?examDetailId=' + thatt.examDetailId + '&userId=' + thatt.userId
    }).then(function (response) {
      thatt.string_answers2 = response.data.data.answerTranslate
      thatt.string_answers3 = response.data.data.answerArticle
    })
    var that = this
  },
  methods: {
    // LHA’s test 分页时handlePageChange(page)
    finishMark: function () {
      let that = this

      axios({
        headers: { Authorization: that.print.Authorization },
        method: 'post',
        url: '/api/studentAnswer/score2',
        params: {
          userId: that.userId,
          examDetailId: that.examDetailId,
          score2: that.score2
        }
      }).then(function (response) {
        axios({
          headers: { Authorization: that.print.Authorization },
          method: 'post',
          url: '/api/studentAnswer/score3',
          params: {
            userId: that.userId,
            examDetailId: that.examDetailId,
            score3: that.score3
          }
        }).then(function (response) {
          axios({
            headers: { Authorization: that.print.Authorization },
            method: 'post',
            url: '/api/studentAnswer/scoreOut',
            params: {
              examDetailId: that.examDetailId
            }
          }).then(function (response) {})
        })
        that.$message({
          message: '分数上报成功',
          type: 'success'
        })
        that.$router.push({ name: 'usercenter' })
      },
      function (err) {
        that.$message.error('分数上报失败，请重新尝试')
      })
    }
  }
}
</script>
