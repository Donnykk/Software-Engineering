<!--<template>-->
<!--  <div>-->
<!--    <h1>个人考试页面</h1>-->
<!--    <div v-for="question in questions" :key="question.Question_id">-->
<!--      <p v-if="question.Question_type === 1">选择题：{{ question.Question_content }}</p>-->
<!--      <input v-if="question.Question_type === 1" type="radio" :name="'answer' + question.Question_id" value="option1" v-model="answers[question.Question_id]">-->
<!--      <label v-if="question.Question_type === 1" :for="'option1' + question.Question_id">{{ question.choice1 }}</label>-->
<!--      <input v-if="question.Question_type === 1" type="radio" :name="'answer' + question.Question_id" value="option2" v-model="answers[question.Question_id]">-->
<!--      <label v-if="question.Question_type === 1" :for="'option2' + question.Question_id">{{ question.choice2 }}</label>-->

<!--      <p v-else>简答题：{{ question.Question_content }}</p>-->

<!--    </div>-->
<!--    <textarea name="textarea"  cols="50" rows="10" >请在此作答</textarea>-->
<!--  </div>-->

<!--</template>-->
<!--<script>-->
<!--import { MessageBox } from 'element-ui';-->
<!--import axios from "axios";-->
<!--export default {-->
<!--  name: 'personalDoExam',-->
<!--  mounted: function () {-->
<!--    // 获取路由参数值-->
<!--    this.examId = this.$route.params.examId;-->
<!--    MessageBox.alert(this.examId, '考试描述', {-->
<!--      confirmButtonText: '确定',-->
<!--      type: 'info',-->
<!--    });-->
<!--    this.getExamContent();-->
<!--  },-->
<!--  data() {-->
<!--    return {-->
<!--      examId:0,-->
<!--      questions: [],-->
<!--      answers: {-->
<!--        answer1: '',-->
<!--        answer2: '',-->
<!--        // 添加其他选择题的答案属性-->
<!--      },-->
<!--    };-->
<!--  },-->
<!--  methods: {-->
<!--    submitExam() {-->
<!--      // 在此处添加提交考试的逻辑-->
<!--      // 可以使用 answers 和 composition 数据进行处理-->
<!--    },-->
<!--    async getExamContent() {-->
<!--      try {-->
<!--        const response = await axios.get(`http://localhost:3000/api/questions/${this.examId}`);-->
<!--        // MessageBox.alert(response.data.data[0], '考试描述', {-->
<!--        //   confirmButtonText: '确定',-->
<!--        //   type: 'info',-->
<!--        // });-->
<!--        this.questions = response.data.data[0];-->
<!--      } catch (error) {-->
<!--        console.error(error);-->
<!--        MessageBox.alert('获取考题内容失败', '错误', {-->
<!--          confirmButtonText: '确定',-->
<!--          type: 'error',-->
<!--        });-->
<!--      }-->
<!--    },-->
<!--  },-->
<!--};-->

<!--</script>-->

<!--<style>-->
<!--h1 {-->
<!--  color: #333;-->
<!--  font-size: 24px;-->
<!--  font-weight: bold;-->
<!--}-->
<!--</style>-->
<template>
  <div>
    <h1>个人考试页面</h1>
    <div v-for="question in questions" :key="question.Question_id">
      <p v-if="question.Question_type === 1">选择题：{{ question.Question_content }}</p>
      <input v-if="question.Question_type === 1" type="radio" :name="'answer' + question.Question_id" value="option1" v-model="answers[question.Question_id]">
      <label v-if="question.Question_type === 1" :for="'option1' + question.Question_id">{{ question.choice1 }}</label>
      <input v-if="question.Question_type === 1" type="radio" :name="'answer' + question.Question_id" value="option2" v-model="answers[question.Question_id]">
      <label v-if="question.Question_type === 1" :for="'option2' + question.Question_id">{{ question.choice2 }}</label>

      <p v-else>简答题：{{ question.Question_content }}</p>
      <textarea v-if="question.Question_type === 2" v-model="answers[question.Question_id]" cols="50" rows="5"></textarea>
    </div>

    <button @click="submitExam">提交</button>
  </div>
</template>

<script>
import axios from 'axios';
import { MessageBox } from 'element-ui';
export default {
  name: 'personalDoExam',
  data() {
    return {
      examId: 0,
      questions: [],
      answers: {},
    };
  },
  mounted() {
    // 获取路由参数值
    this.examId = this.$route.params.examId;
    this.userId = this.$route.params.userId;
    this.userId = this.userId.toString();
    this.getExamContent();
    MessageBox.alert(this.userId, '考试描述', {
      confirmButtonText: '确定',
      type: 'info',
    });
  },
  methods: {
    async getExamContent() {
      try {
        const response = await axios.get(`http://localhost:3000/api/questions/${this.examId}`);
        this.questions = response.data.data[0];
      } catch (error) {
        console.error(error);
      }
    },
    async submitExam() {
      try {
        const response = await axios.post('http://localhost:3000/api/submit', {
          examId: this.examId,
          answers: this.answers,
          userId: this.userId,
        }, {
          headers: {
            'Content-Type': 'application/json',
          },
        });
        // 处理提交成功的响应
        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    },
  },
};
</script>


