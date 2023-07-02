<template>
  <div>
    <el-table
      :data="
        fileList.slice((currentPage - 1) * pagesize, currentPage * pagesize)
      "
      style="width: 100%"
      :default-sort="{ prop: 'date', order: 'descending' }"
      v-loading="loading"
    >
      <!--考试ID-->
      <el-table-column
        prop="examID"
        label="考试ID"
        align="center"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="examDescription"
        label="考试名称"
        align="center"
        width="200"
      ></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="checkPoint(scope.row)">查看成绩</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pagesize"
      background
      align="center"
      layout="total, prev, pager, next, jumper"
      :total="pageTotal"
    >
    </el-pagination>

  </div>
</template>
<!--personalStartExam-->
<script>
import axios from "axios";
import { mapState, mapActions } from "vuex";
import { MessageBox } from 'element-ui';
// import router from '@/router';
export default {
  inject: ["reload"],
  name: "checkScore",
  data() {
    return {
      loading: false,
      //归档表
      fileList: [],
      //考试信息表
      examList: [],
      //初始页
      currentPage: 1,
      //每页的数据
      pagesize: 10,
      //数组总数
      pageTotal: 0,
      //归档总表
      allFileList: [],
      //用户报名归档表
      userFileList: [],
      //缴费状态
      finishTotal: 0,
    };
  },
  computed: {
    ...mapState({
      print: (state) => state.print.all,
      userId: (state) => state.userId.all,
    }),
  },
  mounted: function () {
    this.getExamList();
    var that = this;
  },
  methods: {
    checkPoint: function (row) {
      // 在这里处理开始考试按钮的点击事件
      var that = this;
      MessageBox.confirm('确定查看成绩吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // 用户点击了确定按钮，执行页面跳转的操作
        // router.push('/usercenter/personalDoExam');
        //this.$router.push({ name: 'personalDoExam', params: { examId: row.examID,userId:this.userId.userId } });
        axios
          .all([
            axios({
              headers: { Authorization: this.print.Authorization },
              method: "get",
              url: "/api/studentAnswer?userId=" +
                row.userId + "&examDetailId=" +
                row.examID,
            }),
          ])
          .then(axios.spread(function (response) {
            that.finishTotal = response.data.data.finishTotal;
            if (!that.finishTotal) {
              MessageBox.alert('该堂考试分数未公布！', '未出分').then(() => {
                this.$router.push({ name: "usercenter" });
              });
            }
            else {
              MessageBox.confirm(response.data.data.score, '该堂成绩', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'success',
              }).then(() => {
                this.$router.push({ name: "usercenter" });
              }).catch(() => {
                // 用户点击了取消按钮，什么都不做
              });
            }
          }),)
      }).catch(() => {
        // 用户点击了取消按钮，什么都不做
      });
    },
    //获取还在报名的考试信息
    getExamList: function () {
      var that = this;
      this.loading = true;
      axios
        .all([
          //报名表
          axios({
            headers: { Authorization: this.print.Authorization },
            method: "get",
            url: "/api/userExamEntry/user?userId=" + this.userId.userId,
          }),
          //考试信息表
          axios({
            headers: { Authorization: this.print.Authorization },
            method: "get",
            url: "/api/examDetail",
          }),
        ])
        .then(
          axios.spread(function (fileResponse, examResponse) {
            that.fileList = fileResponse.data.data;
            that.examList = examResponse.data.data;
            that.pageTotal = fileResponse.data.data.length;
            var _that = that;
            that.fileList.forEach((item) => {
              axios({
                headers: { Authorization: that.print.Authorization },
                method: "get",
                url: "/api/examEntry?examEntryId=" + item.examEntryId,
              }).then(function (reponse) {
                for (var i = 0; i < _that.examList.length; i++) {
                  if (
                    reponse.data.data.examDetailId ==
                    _that.examList[i].examDetailId
                  ) {
                    _that.$set(
                      item,
                      "examID",
                      _that.examList[i].examDetailId
                    );
                    _that.$set(
                      item,
                      "examDescription",
                      _that.examList[i].examDescription
                    );
                  }
                }
              });
            });
            that.loading = false;
          })
        );
    },

    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
    },
  },
};
</script>
