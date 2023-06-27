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
      <el-table-column
        prop="examLocation"
        label="考试地点"
        align="center"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="examStartTime"
        label="考试开始时间"
        align="center"
        width="150"
      ></el-table-column>
      <el-table-column
        prop="examEndTime"
        label="考试结束时间"
        align="center"
        width="150"
      ></el-table-column>
      <el-table-column
        prop="location"
        label="座位"
        align="center"
        width="100"
      ></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="startExam(scope.row)">开始考试</el-button>
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
  name: "personalStartExam",
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
    };
  },
  computed: {
    ...mapState({
      print: (state) => state.print.all,
      userId: (state) => state.userId.all,
    }),
  },
  mounted: function () {
    this.getRegistrationList();
    var that = this;
    setTimeout(function () {
      that.getLocation();
    }, 300);
  },
  methods: {
    startExam: function (row) {
      // 在这里处理开始考试按钮的点击事件
      // 您可以访问当前行的数据，例如 row.examDescription、row.examLocation 等
      // 添加您的逻辑代码
      MessageBox.confirm('确定开始考试吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // 用户点击了确定按钮，执行页面跳转的操作
        // router.push('/usercenter/personalDoExam');
        this.$router.push({ name: 'personalDoExam', params: { examId: row.examID,userId:this.userId.userId } });
      }).catch(() => {
        // 用户点击了取消按钮，什么都不做
      });
    },
    //获取还在报名的考试信息
    getRegistrationList: function () {
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
                    _that.$set(
                      item,
                      "examStartTime",
                      _that.examList[i].examStartTime
                    );
                    _that.$set(
                      item,
                      "examEndTime",
                      _that.examList[i].examEndTime
                    );
                    _that.$set(
                      item,
                      "examLocation",
                      _that.examList[i].examLocation
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

    getLocation: function () {
      var that = this;
      axios({
        headers: { Authorization: this.print.Authorization },
        method: "get",
        url: "/api/examLocation/user?userId=" + this.userId.userId,
      }).then(
        function (response) {
          response.data.data.forEach((item) => {
            for (var i = 0; i < that.fileList.length; i++) {
              if (item.userExamEntryId == that.fileList[i].userExamEntryId) {
                that.$set(that.fileList[i], "location", item.location);
                that.$set(
                  // that.fileList[i],
                  // "examLocationId",
                  // item.examLocationId
                );
              }
            }
          });
          that.loading = false;
        },
        function (errpr) {
          that.fileList.forEach((item) => {
            that.$set(item, "location", "未安排座位");
          });
        }
      );
    },
  },
};
</script>

