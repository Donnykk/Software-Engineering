<template>
  <div>
    <el-table :data="scoreList.slice((currentPage - 1) * pagesize, currentPage * pagesize)
      " style="width: 100%" :default-sort="{ prop: 'date', order: 'descending' }" v-loading="loading">
      <el-table-column prop="examDescription" label="考试名称" align="center"></el-table-column>
      <el-table-column prop="examLocation" label="考试地点" align="center"></el-table-column>
      <el-table-column prop="examTime" label="考试时间" align="center"></el-table-column>
      <el-table-column prop="examScore" label="成绩" align="center"></el-table-column>
      <el-table-column prop="examDetail" label="成绩详情" align="center">
        <template slot-scope="scope">
          <el-button @click="DetailDialog = true" size="small">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="DetailDialog" width="400px">
      <el-table :data='score'>
        <el-table-column prop='total' align="center" label='总分'></el-table-column>
        <el-table-column prop='listening' align="center" label='听力'></el-table-column>
        <el-table-column prop='reading' align="center" label='阅读'></el-table-column>
        <el-table-column prop='writing' align="center" label='写作和翻译'></el-table-column>
      </el-table>
      <el-button size="small" style="margin-left: 300px;margin-top: 20px;" type="primary"
        @click="DetailDialog = false">确定</el-button>
    </el-dialog>
    <el-pagination @current-change="handleCurrentChange" :current-page="currentPage" :page-size="pagesize" background
      align="center" layout="total, prev, pager, next, jumper" :total="pageTotal">
    </el-pagination>
  </div>
</template>

<script>
import axios from "axios";
import { mapState, mapActions } from "vuex";
export default {
  inject: ["reload"],
  name: "examResultNotice",
  data() {
    return {
      DetailDialog: false,
      //分数表
      scoreList: [
        {
          examDescription: '大学生四级英语考试',
          examLocation: '南开大学津南校区',
          examTime: '2022-12-12',
          examScore: '610'
        },
        {
          examDescription: '大学生六级英语考试',
          examLocation: '南开大学津南校区',
          examTime: '2022-03-01',
          examScore: '599'
        }
      ],
      score: [
        {
          total: '610',
          listening: '180',
          reading: '220',
          writing: '210'
        }
      ],
      //初始页
      currentPage: 1,
      //每页的数据
      pagesize: 10,
      //数组总数
      pageTotal: 1,
    };
  },
  computed: {
    ...mapState({
      print: (state) => state.print.all,
      userId: (state) => state.userId.all,
    }),
  },
  mounted: function () {
  },
  methods: {
    getScoreList: function () {
      var that = this;
      this.loading = true;
      //获取分数
      axios({
        headers: { Authorization: this.print.Authorization },
        method: "get",
        url: "/api/examScore/user?userId=" + this.userId.userId,
      }).then(function (response) {
        that.scoreList = response.data.data;
        if (that.scoreList != null) {
          that.pageTotal = response.data.data.length;
          //获取考试信息
          var _that = that;
          that.scoreList.forEach((item) => {
            axios({
              headers: { Authorization: that.print.Authorization },
              method: "get",
              url:
                "/api/examDetail?examDetailId=" +
                item.examDetailId,
            }).then(function (response) {
              _that.$set(
                item,
                "examDescription",
                response.data.data[0].examDescription
              );
              _that.$set(
                item,
                "examLocation",
                response.data.data[0].examLocation
              );
              _that.$set(
                item,
                "examStartTime",
                response.data.data[0].examStartTime
              );
              _that.$set(
                item,
                "examEndTime",
                response.data.data[0].examEndTime
              );
            });
          });
        } else {
          that.pageTotal = 0;
          that.scoreList = [];
        }

        that.loading = false;
      });
    },

    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
    },
  },
};
</script>