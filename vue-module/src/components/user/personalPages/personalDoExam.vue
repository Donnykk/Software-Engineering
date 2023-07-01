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
    <div>
      <img :src="html_top_imgUrl" style="height: 100%; width: 100%" />
    </div>
    <nav
      class="navbar navbar-expand-lg navbar-light"
      style="background-color: #e3f2fd"
    >

      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <router-link
              class="nav-link"
              to="/homepage"
              id="navbarDropdown"
              role="button"
              aria-haspopup="true"
              aria-expanded="false"
            >
              首页
            </router-link>
          </li>
        </ul>
      </div>
    </nav>
    <div style="overflow: hidden;">
      <div style="float: left; width: 50%;">
        <div style="height: 1600px;overflow-y: scroll;">
          <img :src="test" alt=""/>
        </div>
      </div>
      <div style="float: right; width: 50%">
        <p style="font-family: SimSun,Arial, sans-serif; font-size: 24px; font-weight: bold;color: #090000; line-height: 1.5; text-align: justify;text-align: center">
          全国四六级考试，统一线上答题卡
        </p>
        <el-main>
          <el-steps :active="active" finish-status="success" align-center>
            <el-step title="第一页"></el-step>
            <el-step title="第二页"></el-step>
            <el-step title="第三页"></el-step>
          </el-steps>
          <template v-if="step == 0">
            <p style="font-family: SimSun,Arial, sans-serif; font-size: 16px; font-weight: bold;color: #090000; text-align: justify;">
              听力部分： In this section, you will hear two long conversations. At the end of each conversation , you willhear four questions. Both the conversation and the questions will be spoken only once.
              After you hear aquestion , you must choose the best answer from the four choices marked A), B), C) and D).
              Then markthe corresponding letter on Answer Sheet 1 with a single line through the centre.
            </p>
            <!-- 听力选择题部分 -->
            <div>
              <ol style="list-style-type: decimal;">
                <li v-for="(question, index) in displayedQuestions" :key="index">
                  {{ question.question }}
                  <div style="display: flex; justify-content: space-between;">
                    <label v-for="(option, optionIndex) in question.options" :key="optionIndex">
                      <input type="radio" :name="'question'+index" :value="option" v-model="answers[index]">
                      {{ option }}
                    </label>
                  </div>
                </li>
              </ol>
            </div>
          </template>
          <template v-if="step == 1">
            <p style="font-family: SimSun,Arial, sans-serif; font-size: 16px; font-weight: bold;color: #090000; text-align: justify;">
              翻译部分： For this part,you are allowed 30 minutes to translate a passage from Chinese into English . You
              should write your answer on Answer Sheet 2.
            </p >
            <div>
              <textarea v-model="string_answers2" style="width: 100%; height: 200px;"></textarea>
            </div>
          </template>
          <template v-if="step == 2">
            <p style="font-family: SimSun,Arial, sans-serif; font-size: 16px; font-weight: bold;color: #090000; text-align: justify;">
              写作部分： For this part, you are allowed 30 minutes to write an essay that begins with the
              sentence "Withthe application of information technology in education , college students
              can now learn in more diverse andefficient ways.” You can make statements, give reasons,
              or cite examples to develop your essay. Youshould write at least 150 words but no more than 200 words .
            </p >
            <div>
              <textarea v-model="string_answers3" style="width: 100%; height: 200px;"></textarea>
            </div>
          </template>
        </el-main>
        <el-footer style="text-align: center">
          <template v-if="active < 2">
            <el-button style="margin-top: 12px" @click="prev">上一页</el-button>
            <el-button style="margin-top: 12px" @click="next">下一页</el-button>
          </template>
          <template v-else>
            <el-button style="margin-top: 12px" @click="prev">上一页</el-button>
            <el-button style="margin-top: 12px" @click="next">下一页</el-button>
            <el-button style="margin-top: 12px" @click="finishExam"
            >完成考试</el-button
            >
          </template>
        </el-footer>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import axios from 'axios'
// 主页标题图片
import test from '../../../assets/answers/2022_12.jpg'
import html_top_imgUrl from '../../../assets/html_top.png'
export default {
  inject: ['reload'],
  name: 'personalDoExam',
  data () {
    return {
      // 我的测试，-----------------------------------------------------\
      html_top_imgUrl: html_top_imgUrl,
      test: test,
      active: 0,
      step: 0,
      questions: [
        {
          question: '题目1',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: '' // 存储用户选择的答案
        },
        {
          question: '题目2',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目3',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目4',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目5',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目6',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目7',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目8',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目9',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目10',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目11',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目12',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目13',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目14',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目15',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目16',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目17',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目18',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目19',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目20',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目21',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目22',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目23',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目24',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        },
        {
          question: '题目25',
          options: ['选项A', '选项B', '选项C', '选项D'],
          answer: ''
        }
        // 其他题目
      ],
      answers: [], // 存储用户选择的答案数组
      string_answers1: '$$$$$$$$$$$$$$$$$$$$$$$$$',
      string_answers2: 'null',
      string_answers3: 'null',
      examDetailId: '',
      userId: '',
      //testList: [require('../../../assets/answers/2022_12.jpg'), require('../../../assets/answers/22.jpg')]
    }
  },
  computed: {
    ...mapState({
      print: (state) => state.print.all,
      userId: (state) => state.userId.all
    }),

    displayedQuestions () {
      return this.questions
    }
  },
  mounted: function () {
    this.examDetailId = this.$route.params.examDetailId
    this.userId = this.$route.params.userId
    //this.test = this.$route.params.test
    var that = this
  },
  methods: {
    // LHA’s test 分页时handlePageChange(page)
    prev () {
      this.$message({
        message: this.test,
        type: "success",
      });
      if (this.active == 0) {
        this.active = 0
      } else {
        this.active--
        this.checkStep(this.active)
      }
    },
    next () {
      if (this.active < 2) {
        this.active++
      }
      this.checkStep(this.active)
    },

    checkStep: function (active) {
      var that = this
      if (active == 0) {
        this.step = 0
        // 显示个人须知
        // 判断是否报名过
      } else if (active == 1) {
        this.step = 1
      } else if (active == 2) {
        // 报名
        this.step = 2
      }
    },

    finishExam: function () {
      let that = this

      for (var i = 0; i < 25; i++) {
        switch (that.answers[i]) {
          case '选项A':
            var t_string = that.string_answers1
            that.string_answers1 = t_string.substring(0, i) + 'A' + that.string_answers1.substring(i + 1, 25)
            break
          case '选项B':
            var tt_string = that.string_answers1
            that.string_answers1 = tt_string.substring(0, i) + 'B' + that.string_answers1.substring(i + 1, 25)
            break
          case '选项C':
            var ttt_string = that.string_answers1
            that.string_answers1 = ttt_string.substring(0, i) + 'C' + that.string_answers1.substring(i + 1, 25)
            break
          case '选项D':
            var tttt_string = that.string_answers1
            that.string_answers1 = tttt_string.substring(0, i) + 'D' + that.string_answers1.substring(i + 1, 25)
            break
          default:
        }
      }
      axios({
        headers: { Authorization: this.print.Authorization },
        method: 'post',
        url: '/api/studentAnswer',
        params: {
          userId: that.userId,
          examDetailId: that.examDetailId,
          answer1: that.string_answers1,
          answer2: 'xx',
          answer3: 'xx',
          answerTranslate: that.string_answers2,
          answerArticle: that.string_answers3,
          score: 0
        }
      }).then(function (response) {
        axios({
          headers: { Authorization: that.print.Authorization },
          method: 'get',
          url: '/api/standardAnswer?examDetailId=' + that.examDetailId,
        }).then(function (response) {
          var getAnswer = response.data.data.answer1;
          axios({
            headers: { Authorization: that.print.Authorization },
            method: 'post',
            url: '/api/studentAnswer/score1',
            params: {
              userId: that.userId,
              examDetailId: that.examDetailId,
              answer1: getAnswer,
            }
          }).then(function (response) {})
        })
        that.$message({
          message: '交卷成功',
          type: 'success'
        })
        that.$router.push({ name: 'usercenter' })
      },
      function (err) {
        that.$message.error('交卷失败，请重新尝试')
      })
    }
  }
}
</script>
