<template>
  <div>
    <el-table
      :data="
        markList.slice((currentPage - 1) * pagesize, currentPage * pagesize)
      "
      style="width: 100%"
      :default-sort="{ prop: 'date', order: 'descending' }"
      v-loading="loading"
    >
      <el-table-column prop="examDetailId" label="考试号"> </el-table-column>
      <el-table-column prop="userId" label="考生号"> </el-table-column>\
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button @click="GetMark(scope.row)" size="阅卷"
          >开始阅卷</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      align="center"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pagesize"
      background
      layout="total, prev, pager, next, jumper"
      :total="pageTotal"
    >
    </el-pagination>
  </div>
</template>

<script>
import axios from 'axios'
import { mapState, mapActions } from 'vuex'
import {MessageBox} from "element-ui";
export default {
  inject: ['reload'],
  name: 'markingPaper',
  data () {
    return {
      loading: false,
      // 存放用户账单的列表
      markList: [],
      // 初始页
      currentPage: 1,
      // 每页的数据
      pagesize: 10,
      // 数据总数
      pageTotal: 0,
      //考试状态
      examState1: 0,
      examState2: false,
      examState3: false
    }
  },
  computed: {
    ...mapState({
      print: (state) => state.print.all,
      userId: (state) => state.userId.all
    })
  },
  mounted: function () {
    this.getMyMark()
    var that = this
  },
  methods: {
    getMyMark: function () {
      var that = this
      axios
        .all([
          axios({
            headers: { Authorization: this.print.Authorization },
            method: 'get',
            url:
              '/api/studentAnswer/all',
          })
        ])
        .then(
          axios.spread((reponse) => {
            // 上面两个请求都完成后，才执行这个回调方法
            that.myMarkList = reponse.data.data
            if (that.myMarkList.length !== 0) {
              for (let i = 0; i < that.myMarkList.length; i++) {
                that.markList.push(that.myMarkList[i])
                that.pageTotal += 1
              }
            }
          })
        )
        .catch((err) => {
          that.$message.error('查无阅卷消息')
          this.loading = false
        })
    },

    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
    },

    GetMark: function (row) {
      var that = this
      axios
        .all([
          axios({
            headers: { Authorization: that.print.Authorization },
            method: "get",
            url: "/api/studentAnswer/state?userId=" +
              row.userId + "&examDetailId=" +
              row.examID,
          }),
          axios({
            headers: { Authorization: that.print.Authorization },
            method: "get",
            url: "/api/studentAnswer?userId=" +
              row.userId + "&examDetailId=" +
              row.examDetailId,
          }),
        ])
        .then(axios.spread(function (responseState, response) {
          that.examState1 = responseState.data.data;
          if (that.examState1 === 2) {
            MessageBox.alert('考生还未答题！', '未答题！').then(() => {
              that.$router.push({ name: "usercenter" });
            });
          }
          that.examState2 = response.data.data.finish2;
          that.examState3 = response.data.data.finish3;
          if (that.examState2 && that.examState3) {
            MessageBox.alert('您已经阅过此卷，不能重复阅卷！', '已阅卷！').then(() => {
              that.$router.push({ name: "usercenter" });
            });
          }
        }),)
      that.$message({
        message: '开始阅卷',
        type: 'info'
      })
      that.$router.push({ name: 'markingDetail' , params: { examDetailId: row.examDetailId, userId: row.userId }})
    }
  }
}
</script>
