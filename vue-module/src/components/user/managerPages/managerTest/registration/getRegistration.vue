<template>
  <div>
    <el-table :data="examList.slice(
      (currentPage - 1) * pagesize,
      currentPage * pagesize
    )" style="width: 100%" @cell-click="ifUpdateState" v-loading="loading">
      <el-table-column prop="examId" label="考试编号" align="center">
      </el-table-column>
      <el-table-column prop="examTypeName" label="考试名称" align="center" width="200">
      </el-table-column>
      <el-table-column prop="examTotalNum" label="总名额" align="center">
      </el-table-column>
      <el-table-column prop="examPresentNum" label="剩余名额" align="center">
      </el-table-column>
      <el-table-column prop="examTime" label="时间" align="center">
      </el-table-column>
      <el-table-column prop="examState" label="状态" align="center">
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" width="180">
        <template slot-scope="scope">
          <el-button type="danger" @click="deleteRegistrationRelease(scope.row)" size="small">删除</el-button>
          <el-button type="info" @click="userListDialog = true" size="small">报名情况</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @current-change="handleCurrentChange" :current-page="currentPage" background align="center"
      layout="total, prev, pager, next, jumper" :total="pageTotal">
    </el-pagination>

    <el-dialog title="报名表" height="500" :visible.sync="userListDialog" v-loading="examLoading">
      <el-table :data="allReg" align="center">
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="realName" label="学生姓名"></el-table-column>
        <el-table-column prop="stuNo" label="学生学号"></el-table-column>
        <el-table-column prop="location" label="考场" width="200px"></el-table-column>
        <el-table-column prop="seat" label="座位"></el-table-column>
        <el-table-column>
          <template slot-scope="scope">
            <el-button icon="el-icon-delete" size="mini" type="danger" @click="deleteUserReg(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import { mapState, mapActions } from "vuex";
export default {
  inject: ["reload"],
  name: "getRegistration",
  data() {
    return {
      loading: false,
      //报名表
      registrationList: [],
      //考试信息表
      examList: [],
      //初始页
      currentPage: 1,
      //每页的数据
      pagesize: 10,
      //数组总数
      pageTotal: 0,
      state: "",
      //全用户表
      allUser: [],
      //全报名表
      allReg: [
        {
          realName: "唐鹏程",
          stuNo: "2011181",
          location: "南开大学津南校区 B402",
          seat: "23",
        },
        {
          realName: "张三",
          stuNo: "2011188",
          location: "南开大学津南校区 B402",
          seat: "28",
        },
        {
          realName: "林俊杰",
          stuNo: "2011132",
          location: "南开大学津南校区 B402",
          seat: "32",
        },
        {
          realName: "陶喆",
          stuNo: "2029011",
          location: "南开大学津南校区 B402",
          seat: "43",
        },
        {
          realName: "怪兽",
          stuNo: "2011231",
          location: "南开大学津南校区 B402",
          seat: "11",
        },
        {
          realName: "陈信宏",
          stuNo: "2013221",
          location: "南开大学津南校区 B402",
          seat: "27",
        },
        {
          realName: "周杰伦",
          stuNo: "2021181",
          location: "南开大学津南校区 B402",
          seat: "22",
        },
        {
          realName: "结巴",
          stuNo: "2021141",
          location: "南开大学津南校区 B402",
          seat: "12",
        },
        {
          realName: "石头",
          stuNo: "2021141",
          location: "南开大学津南校区 B402",
          seat: "12",
        },
        {
          realName: "李四",
          stuNo: "2021241",
          location: "南开大学津南校区 B402",
          seat: "9",
        },
      ],
      //考场表
      locationList: [],
      //显示用户报名表dialog
      userListDialog: false,
      //报名进度dialog
      examLoading: false,
      teacher: "",
      examDetailId: "",
      examEntryId: "",
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
  },
  methods: {
    getRegistrationList: function () {
      var that = this;
      this.loading = true;
      axios
        .all([
          //考试信息表
          axios({
            headers: { Authorization: this.print.Authorization },
            method: "get",
            url: "/api/examDetail",
          }),
        ])
        .then(
          axios.spread(function (examResponse) {
            that.examList = examResponse.data.data;
            that.loading = false;
          })
        )
        .catch((err) => {
          that.$message.error("获取失败");
          that.loading = false;
        });
    },

    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
    },

    ifUpdateState: function (row, column, cell, event) {
      if (row.ifUpdate == true) {
        if (row.state == "FINISH") {
          this.$message({
            message: "报名已结束，无法更改",
            type: "info",
          });
        } else {
          //防止点到按钮
          if (column.fixed != "right") {
            this.$message({
              message: "确认改变报名状态",
              type: "info",
            });
            row.ifUpdate = false;
            this.state = row.state;
          } else {
            this.$message({
              message: "只能改变报名状态",
              type: "warning",
            });
          }
        }
      }
    },

    updateState: function (row) {
      var that = this;
      row.ifUpdate = true;
      if (row.state == this.state) {
        that.$message({
          message: "未进行更改，取消编辑",
          type: "info",
        });
      } else {
        axios({
          headers: { Authorization: this.print.Authorization },
          method: "put",
          url: "/api/examEntry",
          params: {
            examEntryId: row.examEntryId,
            examDetailId: row.examDetailId,
            term: row.term,
            contact: row.contact,
            state: this.state,
          },
        }).then(
          function (reponse) {
            that.$message({
              message: "更改成功",
              type: "success",
            });
            that.reload();
          },
          function (err) {
            that.$message.error("更改失败");
          }
        );
      }
    },

    deleteRegistrationRelease: function (row) {
      var that = this;
      axios({
        headers: { Authorization: this.print.Authorization },
        method: "delete",
        url: "/api/examEntry?examEntryId=" + row.examEntryId,
      }).then(
        function (reponse) {
          that.$message({
            message: "删除成功",
            type: "success",
          });
          that.reload();
        },
        function (err) {
          that.$message.error("删除失败");
        }
      );
    },

    getRegistrationUserList: function (row) {
      var that = this;
      this.userList = [];
      this.userListDialog = true;
      this.examLoading = true;
      this.examDetailId = row.examDetailId;
      this.examEntryId = row.examEntryId;
      if (row.number < 1000) {
        //mySQL获得用户信息
        axios
          .all([
            axios({
              headers: {
                Authorization: this.print.Authorization,
              },
              method: "get",
              url: "/api/users?pageNum&pageSize=1000000",
            }),
            axios({
              headers: { Authorization: this.print.Authorization },
              method: "get",
              url: "/api/userExamEntry?examEntryId=" + row.examEntryId,
            }),
          ])
          .then(
            axios.spread(function (userResponse, userEntryReponse) {
              that.allUser = userResponse.data.data;
              that.allReg = userEntryReponse.data.data;
              //显示报名人姓名
              that.allReg.forEach((item) => {
                for (var i = 0; i < that.allUser.length; i++) {
                  if (item.userId == that.allUser[i].userId) {
                    that.$set(item, "email", that.allUser[i].email);
                    var _that = that;
                    axios({
                      headers: { Authorization: that.print.Authorization },
                      method: "get",
                      url: "/api/userInfo?username=" + that.allUser[i].userName,
                    }).then(function (reponse) {
                      _that.$set(item, "realName", reponse.data.data.realName);
                      _that.$set(item, "major", reponse.data.data.major);
                      _that.$set(item, "stuNo", reponse.data.data.stuNo);
                      _that.$set(
                        item,
                        "className",
                        reponse.data.data.className
                      );
                    });
                    i = that.allUser.length;
                  }
                }
              });
            })
          );
      } else {
        //redis获得报名用户信息
        axios
          .all([
            axios({
              headers: {
                Authorization: this.print.Authorization,
              },
              method: "get",
              url: "/api/users?pageNum&pageSize=1000000",
            }),
            axios({
              headers: { Authorization: this.print.Authorization },
              method: "get",
              url: "/api/userExamEntry/cache?examEntryId=" + row.examEntryId,
            }),
          ])
          .then(
            axios.spread(function (userResponse, userEntryReponse) {
              that.allUser = userResponse.data.data;
              that.allReg = userEntryReponse.data.data;
              //显示报名人姓名
              that.allReg.forEach((item) => {
                for (var i = 0; i < that.allUser.length; i++) {
                  if (item.userId == that.allUser[i].userId) {
                    that.$set(item, "email", that.allUser[i].email);
                    var _that = that;
                    axios({
                      headers: { Authorization: that.print.Authorization },
                      method: "get",
                      url: "/api/userInfo?username=" + that.allUser[i].userName,
                    }).then(function (reponse) {
                      _that.$set(item, "realName", reponse.data.data.realName);
                      _that.$set(item, "major", reponse.data.data.major);
                      _that.$set(item, "stuNo", reponse.data.data.stuNo);
                      _that.$set(
                        item,
                        "className",
                        reponse.data.data.className
                      );
                    });
                    i = that.allUser.length;
                  }
                }
              });
            })
          );
      }
      this.examLoading = false;

      axios({
        headers: { Authorization: this.print.Authorization },
        method: "get",
        url: "/api/examLocation/examDetail",
        params: {
          examDetailId: this.examDetailId,
        },
      }).then(function (reponse) {
        that.locationList = reponse.data.data;
        that.allReg.forEach((item) => {
          for (var i = 0; i < that.locationList.length; i++) {
            if (item.userId == that.locationList[i].userId) {
              that.$set(item, "location", that.locationList[i].location);
              i = that.locationList.length;
            }
          }
        });
      });
    },

    //删除报名信息记录，包含考场
    deleteUserReg: function (row) {
      var that = this;
      axios
        .all([
          axios({
            headers: { Authorization: this.print.Authorization },
            method: "delete",
            url: "/api/userExamEntry",
            params: {
              examEntryId: row.examEntryId,
              userId: row.userId,
            },
          }),
          axios({
            headers: { Authorization: this.print.Authorization },
            method: "delete",
            url: "/api/examLocation/user",
            params: {
              userId: row.userId,
            },
          }),
        ])
        .then(
          axios.spread(function (delRegResponse, delLocResponse) {
            that.$message({
              message: "删除信息成功",
              type: "success",
            });
            that.reload();
          })
        );
    },

    arrangeSeat: function () {
      var that = this;
      if (this.teacher != "")
        this.allReg.forEach(function (item, index) {
          var _that = that;
          setTimeout(function () {
            axios({
              headers: { Authorization: _that.print.Authorization },
              method: "post",
              url: "/api/examLocation",
              params: {
                examDetailId: _that.examDetailId,
                location: index + 1,
                userId: item.userId,
                teacher: _that.teacher,
                userExamEntryId: item.userExamEntryId,
              },
            }).then(
              function (reponse) {
                _that.reload();
              },
              function (err) {
                _that.$message.error("安排考场失败");
              }
            );
          }, 5000);
        });
      else
        this.$message({
          message: "教师姓名未输入",
          type: "warning",
        });
    },
  },
};
</script>