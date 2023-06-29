<template>
  <div class="container">
    <el-table :data="examList.slice((currentPage - 1) * pagesize, currentPage * pagesize)
      " style="width: 100%" v-loading="loading">
      <el-table-column prop="examDescription" label="考试名称">
      </el-table-column>
      <el-table-column prop="examTime" label="考试时间" sortable>
      </el-table-column>
      <el-table-column prop="examPlace" label="考试地点" sortable>
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center">
        <template slot-scope="scope">
          <el-button @click="registrationDialogVisible = true" size="small">发布报名</el-button>
          <el-popconfirm confirmButtonText="是的" cancelButtonText="不用了" icon="el-icon-info" iconColor="red"
            title="这是一段内容确定删除吗？" @onConfirm="registrationRelease">
            <el-button type="danger" size="small" slot="reference">删除报名</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @current-change="handleCurrentChange" :current-page="currentPage" background align="center"
      layout="total, prev, pager, next, jumper" :total="pageTotal">
    </el-pagination>

    <el-dialog :title="registrationDialogVisibleTitle" :visible.sync="registrationDialogVisible" width="30%" center>
      <el-form ref="form" :model="registrationForm" label-width="80px">
        <el-form-item label="考试ID" hidden>
          <el-input v-model="registrationForm.examDetailId"></el-input>
        </el-form-item>
        <el-form-item label="考试人数">
          <el-input v-model="registrationForm.number"></el-input>
        </el-form-item>
        <el-form-item label="联系人">
          <el-input v-model="registrationForm.contact"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="registrationDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="registrationRelease">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import { mapState, mapActions } from "vuex";
export default {
  inject: ["reload"],
  name: "setRegistration",
  data() {
    return {
      loading: false,
      //考试列表
      examList: [
        {
          examDescription: "大学生英语六级考试",
          examTime: "2023-06-25",
          examPlace: "南开大学津南校区"
        },
        {
          examDescription: "大学生英语六级考试",
          examTime: "2023-06-25",
          examPlace: "南开大学八里台校区"
        },
      ],
      //初始页
      currentPage: 1,
      //每页的数据
      pagesize: 10,
      //数组总数
      pageTotal: 100000,

      //发布考试报名的dialog
      registrationDialogVisible: false,
      //dialog标题
      registrationDialogVisibleTitle: "",
      //发布考试报名的form
      registrationForm: {
        examDetailId: "",
        //SH 上学期, FH 下学期
        term: "SH",
        number: "",
        contact: "",
      },
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
    getExam: function () {
      var that = this;
      this.loading = true;
      axios({
        headers: { Authorization: this.print.Authorization },
        method: "get",
        url: "/api/examDetail",
      }).then(
        function (reponse) {
          that.examList = reponse.data.data;
          that.pageTotal = reponse.data.data.length;
          that.loading = false;
        },
        function (err) {
          that.$message.error("获取失败");
          that.loading = false;
        }
      );
    },

    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
    },

    openRegistrationRelease: function (row) {
      this.registrationDialogVisibleTitle = row.examDescription + "报名发布";
      this.registrationDialogVisible = true;
      this.registrationForm.examDetailId = row.examDetailId;
      this.registrationForm.contact = this.print.username;
    },

    registrationRelease: function () {
      this.registrationDialogVisible = false;
      examList = [
        {
          examDescription: "大学生英语六级考试",
          examTime: "2023-06-25",
          examPlace: "南开大学八里台校区"
        },
      ];
      this.$router.replace({
        path: './blank',
        name: 'blankCall'
      });
    },

    deleteRegistrationRelease: function (row) {
      var that = this;
      axios({
        headers: { Authorization: this.print.Authorization },
        method: "delete",
        url: "/api/examEntry/examDetail?examDetailId=" + row.examDetailId,
      }).then(
        function (reponse) {
          that.$message({
            message: "删除成功，请前往查询页面查看",
            type: "success",
          });
        },
        function (err) {
          that.$message.error("删除失败");
        }
      );
    },
  },
};
</script>