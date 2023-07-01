<template>
  <div class="container">
    <br />
    <br />
    <br />
    <el-table
      v-if="ifMain"
      v-loading="loading"
      :data="
        messageList.slice((currentPage - 1) * pagesize, currentPage * pagesize)
      "
    >
      <el-table-column prop="examDetailId" label="考试id" align="center">
      </el-table-column>
      <el-table-column prop="examDescription" label="考试描述" width="150" align="center">
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="getMessageDetail(scope.row)"
          >查看</el-button
          >
          <el-button
            type="danger"
            size="mini"
            @click="deleteMessage(scope.row, scope.$index)"
          >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      background
      align="center"
      layout="total, prev, pager, next, jumper"
      :total="pageTotal"
      v-if="ifMain"
    >
    </el-pagination>

    <!-- 显示/更新主页内容 -->
    <el-form :model="messageForm" v-if="ifShowMessage" label-width="120px">
      <el-form-item label="考试id">
        <el-input v-model="messageForm.examDetailId" readonly></el-input>
      </el-form-item>
      <el-form-item label="考试描述">
        <el-input v-model="messageForm.examDescription" readonly></el-input>
      </el-form-item>
      <el-form-item label="修改时间">
        <el-input v-model="messageForm.subDate" readonly></el-input>
      </el-form-item>
      <el-form-item label="选择题答案">
        <el-input v-model="messageForm.answerSheet" :readonly="ifReadonly"></el-input>
      </el-form-item>
      <el-form-item label="上传文件">
        <template>
          <div>
            <el-avatar v-image-preview :src="imageUrl" :size="100"></el-avatar
            ><el-button
            @click="uploadExamDialog = true"
            size="small"
          >上传照片</el-button
          >
          </div>
        </template>
      </el-form-item>
      <el-form-item label="文件路径">
        <el-input v-model="messageForm.path" :readonly="ifReadonly"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="returnMain">返回</el-button>
        <el-button type="primary" @click="beforeUpdate">{{
            updateButtonName
          }}</el-button>
        <el-button type="success" @click="updateMessage" v-if="!ifReadonly"
        >更新</el-button
        >
      </el-form-item>
    </el-form>
    <el-dialog :visible.sync="uploadExamDialog" width="400px">
      <el-upload
        class="upload-demo"
        ref="upload"
        action=""
        accept="image/jpeg, image/png"
        :on-remove="handleRemove"
        :auto-upload="false"
        :on-change="uploadImg"
        list-type="picture"
        :file-list="fileUpLoadList"
        :limit="1"
      >
        <el-button slot="trigger" size="small" type="primary"
        >选取文件</el-button
        >
        <el-button
          @click="uploadExam"
          style="margin-left: 10px"
          size="small"
          type="success"
        >上传照片</el-button
        >
        <div slot="tip" class="el-upload__tip">
          只能上传jpg/png文件，且不超过1MB
        </div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import { mapState, mapActions } from "vuex";
export default {
  inject: ["reload"],
  name: "adminEdit",
  data() {
    return {
      //时间
      nowDate: "",
      loading: false,
      //显示主页
      ifMain: true,
      //显示表单
      ifMessage: false,
      ifImg: false,
      //查看消息
      ifShowMessage: false,
      //没编辑就不能更新
      ifReadonly: true,
      //更新按钮更改显示
      updateButtonName: "更新",
      //初始页
      currentPage: 1,
      //每页的数据
      pagesize: 10,
      //数组总数
      pageTotal: 0,
      //存放消息
      messageList: [],
      //主页消息表单
      messageForm: {
        examDetailId: "",
        examDescription: "",
        text: "",
        subDate: "",
        answerSheet: "",
        path: "",
      },

      carouselId: "",
      //主页轮播图片表单
      fileUpLoadList: [],
      file: new FormData(),
      file: new window.FormData(),
      fileName: "",
      imageName: "",
      //图片列表
      imageList: [],
      //卡片选择all展示所有图
      activeName: "all",
      //用户信息表
      userList: [],
      //不同类别图片不同用用户表
      otherUser: [],
      //判断是否是教师
      teacher: true,
      t_file: null,
      uploadExamDialog: false,
      //上传头像的file
      fileData: new FormData(),
      fileData: new window.FormData(),
      //图片名字
      fileName: "",
    };
  },
  computed: {
    ...mapState({
      print: (state) => state.print.all,
      userId: (state) => state.userId.all,
    }),
  },
  created: function () {
    var aData = new Date();
    //console.log(aData);
    //Wed Aug 21 2019 10:00:58 GMT+0800 (中国标准时间)
    this.newData =
      aData.getFullYear() +
      "-" +
      (aData.getMonth() + 1) +
      "-" +
      aData.getDate();
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
          //报名表
          axios({
            headers: { Authorization: that.print.Authorization },
            method: "get",
            url: "/api/examEntry/all?pageNum&pageSize",
          }),
          //考试信息表
          axios({
            headers: { Authorization: that.print.Authorization },
            method: "get",
            url: "/api/examDetail",
          }),
        ])
        .then(
          axios.spread(function (regResponse, examResponse) {
            that.messageList = regResponse.data.data;
            that.pageTotal = regResponse.data.data.length;
            that.examList = examResponse.data.data;
            that.getListToge();
          })
        );
    },

    getListToge: function () {
      var that = this;
      this.messageList.forEach((item) => {
        for (var i = 0; i < this.examList.length; i++) {
          if (this.examList[i].examDetailId === item.examDetailId) {
            this.$set(item, "examDetailId", this.examList[i].examDetailId);
            this.$set(item, "examDescription", this.examList[i].examDescription);
          }
        }
      });
      this.loading = false;
    },

    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
    },

    openMessageForm: function () {
      this.ifMessage = !this.ifMessage;
      this.ifShowMessage = false;
      if (this.ifMessage === false) this.ifMain = true;
      else this.ifMain = false;
      this.messageForm = {};
      this.messageForm.subDate = this.newData;
    },

    resetMessageForm: function () {
      this.messageForm = {};
      this.messageForm.subDate = this.newData;
    },

    getMessageDetail: function (row) {
      var that = this;
      axios({
        headers: { Authorization: that.print.Authorization },
        method: "get",
        url: "/api/standardAnswer?examDetailId=" + row.examDetailId,
      }).then(
        function (response) {
          that.messageForm = {
            examDetailId: row.examDetailId,
            examDescription: row.examDescription,
            subDate: that.newData,
            answerSheet: response.data.data.answer1,
            text: 'test',
            path: 'test',
          };
          that.ifShowMessage = true;
          that.ifMain = false;
        },
        function (err) {
          that.$message.error("获取试卷详情失败");
        }
      );
    },

    deleteMessage: function (row, index) {
      var that = this;

      axios({
        headers: { Authorization: this.print.Authorization },
        method: "delete",
        url: "/api/standardAnswer?examDetailId=" + row.examDetailId,
      }).then(
        function (response) {
          that.$message({
            message: "删除成功",
            type: "success",
          });
        },
        function (err) {
          that.$message.error("删除失败！");
        }
      );
    },

    returnMain: function () {
      this.ifShowMessage = false;
      this.ifMain = true;
    },

    beforeUpdate: function () {
      this.ifReadonly = !this.ifReadonly;
      if (this.ifReadonly == true) {
        this.updateButtonName = "更新";
      } else this.updateButtonName = "取消编辑";
    },

    updateMessage: function () {
      var that = this;
      axios({
        headers: { Authorization: that.print.Authorization,},
        method: "post",
        url: "/api/standardAnswer/change",
        params: {
          examDetailId: that.messageForm.examDetailId,
          answer1: that.messageForm.answerSheet,
          answer2: "$$",
          answer3: "$$",
        },
      }).then(
        function (response) {
          that.$message({
            message: "更改信息成功",
            type: "success",
          });
          that.reload();
        },
        function (err) {
          that.$message.error("更改信息失败");
        }
      );
    },

    uploadImg: function (file) {
      const isLt2M = file.size / 1024 / 1024 < 10;
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 10MB!");
      }
      this.fileData = file.raw;
      this.fileName =
        file.raw.name.slice(0, file.raw.name.length - 4) + this.userId.userId;
    },

    handleRemove(file, fileList) {
      console.log(file, fileList);
      this.fileUpLoadList = [];
    },

    uploadExam: function () {
      let param = new FormData();
      param.append("file", this.fileData);
      param.append("examDetailId", this.messageForm.examDetailId);
      let config = {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: this.print.Authorization,
        }, //这里是重点，需要和后台沟通好请求头，Content-Type不一定是这个值
      }; //添加请求头
      var that = this;
      axios
        .all([
          axios.post("/api/standardAnswer/photo", param, config),
        ])
        .then(
          axios.spread(function (infoResponse) {
            that.$message({
              message: "上传照片成功",
              type: "success",
            });
            that.reload();
          })
        );
    },
  },
};
</script>
