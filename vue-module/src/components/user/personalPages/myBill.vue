<template>
  <div>
    <el-table
      :data="
        billList.slice((currentPage - 1) * pagesize, currentPage * pagesize)
      "
      style="width: 100%"
      :default-sort="{ prop: 'date', order: 'descending' }"
      v-loading="loading"
    >
      <el-table-column prop="examDescription" label="考试内容" fixed></el-table-column>
      <el-table-column prop="examDetailId" label="考试号"> </el-table-column>
      <el-table-column prop="myState" :formatter="stateFormat" label="缴费情况"></el-table-column>
      <el-table-column prop="myMoney" label="缴费金额"> </el-table-column>
      <el-table-column prop="myTime" label="缴费时间"> </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button @click="GetCharge(scope.row)" size="缴费"
          >前往缴费</el-button
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
import axios from "axios";
import { mapState, mapActions } from "vuex";
export default {
  inject: ["reload"],
  name: "myBill",
  data() {
    return {
      loading: false,
      //存放用户账单的列表
      billList: [],
      //初始页
      currentPage: 1,
      //每页的数据
      pagesize: 10,
      //数据总数
      pageTotal: 0,
    };
  },
  computed: {
    ...mapState({
      print: (state) => state.print.all,
      userId: (state) => state.userId.all,
    }),
  },
  mounted: function () {
    this.getMyBill();
    var that = this;
    setTimeout(function () {
      that.getExamMessage();
    }, 300);
  },
  methods: {
    getMyBill: function () {
      var that = this;
      axios
        .all([
          //获取用户的全部账单
          axios({
            headers: { Authorization: this.print.Authorization },
            method: "get",
            url:
              "/api/myBill?pageNum&pageSize=100000&userId=" +
              this.userId.userId,
          }),
        ])
        .then(
          axios.spread((responseBill) => {
            // 上面两个请求都完成后，才执行这个回调方法
            that.myBillList = responseBill.data.data;
            if (that.myBillList.length !== 0) {
              for (let i = 0; i < that.myBillList.length; i++) {
                that.billList.push(that.myBillList[i]);
                that.pageTotal += 1;
              }
            }
          })
        )
        .catch((err) => {
          that.$message.error("查无账单消息");
          this.loading = false;
        });
    },

    stateFormat(row, column) {
      if (row.myState === true) {
        return '是';
      }
      else
      {
        return '否';
      }
    },

    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
    },

    GetCharge: function (row) {
      var that = this;
      if (row.myState === true) {
        that.$message({
          message: "您已经缴过费了",
          type: "info",
        });
        that.$router.push({ name: "usercenter" });
        return;
      }
      axios({
        headers: { Authorization: this.print.Authorization },
        method: "post",
        url: "/api/myBill/finish",
        params: {
          userId: row.userId,
          examTypeId: row.examTypeId,
          examDetailId: row.examDetailId,
          realName: row.realName,
          examDescription: row.examDescription,
          myMoney: row.myMoney,
          myState: row.myState,
        },
      }).then(function (response) {
        that.$message({
          message: "缴费成功",
          type: "success",
        });
        that.$router.push({ name: "usercenter" });
      },
      function (err) {
        that.$message.error("缴费失败，请重新尝试");
        that.$router.push({ name: "usercenter" });
      });
    },
  },
};
</script>
