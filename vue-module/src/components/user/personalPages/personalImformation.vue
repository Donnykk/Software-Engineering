<template>
  <div>
    <br />
    <el-container>
      <el-header style="text-align: center; height: 100px">
        <div class="block">
          <el-avatar v-image-preview :src="imageUrl" :size="100"></el-avatar
          ><el-button
          @click="uploadExamDialog = true"
          v-if="haveExam"
          size="small"
        >上传照片</el-button
        >
          <el-button v-else type="danger" @click="deleteExam" size="small"
          >删除照片</el-button
          >
        </div>
      </el-header>
      <el-main>
        <el-form :model="personInfo" class="demo-ruleForm" v-if="ifUpdate">
          <div class="form-group">
            <el-form-item prop="realName"
            >姓名
              <el-input
                type="text"
                autocomplete="off"
                v-model="personInfo.realName"
              /></el-form-item>
          </div>
          <div class="form-group">
            <el-form-item prop="mySex"
            >性别
              <el-input
                type="text"
                autocomplete="off"
                v-model="personInfo.mySex"
              /></el-form-item>
          </div>
          <div class="form-group">
            <el-form-item prop="mySchool"
            >学校
              <el-input
                type="text"
                autocomplete="off"
                v-model="personInfo.mySchool"
              /></el-form-item>
          </div>
          <div class="form-group">
            <el-form-item prop="myArea"
            >校区
              <el-input
                type="text"
                autocomplete="off"
                v-model="personInfo.myArea"
              /></el-form-item>
          </div>
          <div class="form-group">
            <el-form-item prop="myDepartment"
            >院系
              <el-input
                type="text"
                autocomplete="off"
                v-model="personInfo.myDepartment"
              /></el-form-item>
          </div>
          <fieldset disabled>
            <div class="form-group">
              <el-form-item prop="stuNo"
              >学号
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfo.stuNo"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="major"
              >专业
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfo.major"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="className"
              >专业班级
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfo.className"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="myEducation"
              >学历
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfo.myEducation"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="myIdType"
              >证件类型
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfo.myIdType"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="identificationNumber"
              >身份证号码
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfo.identificationNumber"
                /></el-form-item>
            </div>
          </fieldset>
          <el-button @click="changeIfUpdate">更改信息</el-button>
        </el-form>

        <el-form
          :model="personInfoUpdate"
          ref="personInfoUpdate"
          :rules="rule"
          class="demo-ruleForm"
          v-if="!ifUpdate"
        >
          <fieldset>
            <div class="form-group">
              <el-form-item prop="u_realName"
              >姓名
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfoUpdate.u_realName"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="u_mySex"
              >性别
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfoUpdate.u_mySex"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="u_mySchool"
              >学校
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfoUpdate.u_mySchool"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="u_myArea"
              >校区
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfoUpdate.u_myArea"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="u_myDepartment"
              >院系
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfoUpdate.u_myDepartment"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="u_stuNo"
              >学号
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfoUpdate.u_stuNo"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="u_major"
              >专业<br />
                <el-select
                  v-model="personInfoUpdate.u_major"
                  placeholder="请选择"
                  @change="getClassList(personInfoUpdate.u_major)"
                >
                  <el-option
                    v-for="item in majorList"
                    :key="item.discipline"
                    :label="item.major + '系' + item.discipline"
                    :value="item.discipline"
                  >
                  </el-option> </el-select
                ></el-form-item>
            </div>
            <div class="form-group" v-if="chooseMajor">
              <el-form-item prop="u_className"
              >专业班级<br />
                <el-select
                  v-model="personInfoUpdate.u_className"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in classList"
                    :key="item"
                    :label="item"
                    :value="item"
                  >
                  </el-option> </el-select
                ></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="u_myEducation"
              >学历
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfoUpdate.u_myEducation"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="u_myIdType"
              >证件类型
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfoUpdate.u_myIdType"
                /></el-form-item>
            </div>
            <div class="form-group">
              <el-form-item prop="u_identificationNumber"
              >身份证号码
                <el-input
                  type="text"
                  autocomplete="off"
                  v-model="personInfoUpdate.u_identificationNumber"
                /></el-form-item>
            </div>
          </fieldset>
          <el-button type="button" @click="changeIfUpdate">取消更改</el-button>
          <el-button type="button" @click="dialogVisible = true"
          >更改信息</el-button
          >

          <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
            <span>确认修改个人信息吗？</span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button
                type="primary"
                @click="updateInformation('personInfoUpdate')"
              >确 定</el-button
              >
            </span>
          </el-dialog>
        </el-form>
      </el-main>
      <el-footer></el-footer>
    </el-container>

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
import { mapState, mapActions } from "vuex";
import axios from "axios";
export default {
  inject: ["reload"],
  name: "personalImformation",
  data() {
    return {
      personInfo: {
        realName: "",
        stuNo: "",
        major: "计算机科学与技术",
        className: "软件工程徐思涵老师班",
        identificationNumber: "",
        mySchool: '南开大学',
        myArea: '南开大学津南校区',
        myEducation: '本科',
        myDepartment: '计算机科学与技术',
        mySex: '',
        myIdType: '中华人民共和国居民身份证',
      },
      ifUpdate: true,

      //获得专业列表
      majorList: [],
      //获得班级列表
      classList: [],
      chooseMajor: false,

      //更新专用，判断是录入还是更新
      personInfoUpdate: {
        u_realName: "",
        u_stuNo: "",
        u_major: "计算机科学与技术",
        u_className: "软件工程徐思涵老师班",
        u_identificationNumber: "",
        u_mySchool: '南开大学',
        u_myArea: '南开大学津南校区',
        u_myEducation: '本科',
        u_myDepartment: '计算机科学与技术',
        u_mySex: '',
        u_myIdType: '中华人民共和国居民身份证',
      },
      dialogVisible: false,
      //头像dialog
      uploadExamDialog: false,
      //获取头像
      imageFile: {},
      //上传头像的file
      fileData: new FormData(),
      fileData: new window.FormData(),
      //图片名字
      fileName: "",
      //是否有头像
      haveExam: true,
      //图片地址
      imageUrl: "",
      //图片id
      imageId: "",
      //主页轮播图片表单
      fileUpLoadList: [],
      //获得全部头像，为了小黑
      allImage: [],

      rule: {
        u_realName: [
          { required: true, message: "请输入姓名", trigger: "blur" },
        ],
        u_stuNo: [
          { required: true, message: "请输入学号", trigger: "blur" },
          { min: 7, max: 7, message: "请输入正确的学号", trigger: "blur" },
        ],
        u_major: [{ required: true, message: "请输入专业", trigger: "blur" }],
        u_className: [
          { required: true, message: "请输入班级号", trigger: "blur" },
        ],
        u_identificationNumber: [
          { required: true, message: "请输入身份证", trigger: "blur" },
          {
            min: 18,
            max: 18,
            message: "请如实填写18位身份证号码",
            trigger: "blur",
          },
          {
            required: true,
            pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
            message: "身份证格式错误，请输入正确的身份证号码",
            trigger: "blur",
          },
        ],
        u_mySchool: [
          { required: true, message: "请输入你的大学", trigger: "blur" },
        ],
        u_myArea: [
          { required: true, message: "请输入你所在校区", trigger: "blur" },
        ],
        u_myEducation: [
          { required: true, message: "请输入你的学历", trigger: "blur" },
        ],
        u_myDepartment: [
          { required: true, message: "请输入你的院系", trigger: "blur" },
        ],
        u_mySex: [
          { required: true, message: "请输入你的性别", trigger: "blur" },
        ],
        u_myIdType: [
          { required: true, message: "请输入你的证件类型", trigger: "blur" },
        ],
      },
    };
  },
  mounted: function () {
    this.getImformation();
  },
  computed: {
    ...mapState({
      print: (state) => state.print.all,
      userId: (state) => state.userId.all,
    }),
  },
  methods: {
    getImformation: function () {
      var that = this;
      axios({
        headers: {
          Authorization: this.print.Authorization,
        },
        method: "get",
        url: "/api/userInfo?username=" + this.print.username,
      }).then(
        function (reponse) {
          that.personInfo = reponse.data.data;

          that.personInfoUpdate.u_realName = reponse.data.data.realName;
          that.personInfoUpdate.u_stuNo = reponse.data.data.stuNo;
          that.personInfoUpdate.u_major = reponse.data.data.major;
          that.personInfoUpdate.u_className = reponse.data.data.className;
          that.personInfoUpdate.u_identificationNumber =
            reponse.data.data.identificationNumber;
          that.personInfoUpdate.u_mySchool = reponse.data.data.mySchool;
          that.personInfoUpdate.u_myArea = reponse.data.data.myArea;
          that.personInfoUpdate.u_myEducation = reponse.data.data.myEducation;
          that.personInfoUpdate.u_myDepartment = reponse.data.data.myDepartment;
          that.personInfoUpdate.u_mySex = reponse.data.data.mySex;
          that.personInfoUpdate.u_myIdType = reponse.data.data.myIdType;
        },
        function (err) {
          that.$message.error("你没有信息，请尽快填写");
        }
      );

      //获取考试准考证照片
      axios({
        headers: { Authorization: this.print.Authorization },
        method: "get",
        url: "/api/image/user?userId=" + this.userId.userId,
      }).then(function (response) {
        that.imageFile = response.data.data;
        that.imageFile = that.imageFile.filter((item) => item.tag == "Exam");
        if (that.imageFile.length == 0) {
          that.haveExam = true;
          //获得小黑头像
          var _that = that;
          axios({
            method: "get",
            url: "/api/image/tag?tag=Show",
          }).then(function (response) {
            _that.allImage = response.data.data;
            _that.allImage.forEach((img) => {
              if (img.imageName == "black.") {
                _that.imageUrl = img.url;
              }
            });
          });
        } else {
          that.haveExam = false;
          that.imageUrl = that.imageFile[0].url;
        }
      });
    },

    changeIfUpdate: function () {
      this.ifUpdate = !this.ifUpdate;
      this.personInfoUpdate.u_realName = this.personInfo.realName;
      this.personInfoUpdate.u_stuNo = this.personInfo.stuNo;
      this.personInfoUpdate.u_major = this.personInfo.major;
      this.personInfoUpdate.u_className = this.personInfo.className;
      this.personInfoUpdate.u_identificationNumber = this.personInfo.identificationNumber;
      this.personInfoUpdate.u_mySchool = this.personInfo.mySchool;
      this.personInfoUpdate.u_myArea = this.personInfo.myArea;
      this.personInfoUpdate.u_myEducation = this.personInfo.myEducation;
      this.personInfoUpdate.u_myDepartment = this.personInfo.myDepartment;
      this.personInfoUpdate.u_mySex = this.personInfo.mySex;
      this.personInfoUpdate.u_myIdType = this.personInfo.myIdType;
      if (this.personInfoUpdate.u_major != "") this.chooseMajor = true;
      else this.chooseMajor = false;

      //获得专业列表
      var that = this;
      axios({
        headers: { Authorization: this.print.Authorization },
        method: "get",
        url: "/api/major/all?pageNum&pageSize=100000",
      }).then(function (response) {
        for (var i = 0; i < response.data.data.length; i++) {
          that.majorList[i] = {
            major: response.data.data[i].major,
            discipline: response.data.data[i].discipline,
          };
        }
        that.majorList = that.unique(that.majorList);
      });
    },

    //去掉相同项
    unique(arr) {
      const res = new Map();
      return arr.filter(
        (arr) => !res.has(arr.discipline) && res.set(arr.discipline, 1)
      );
    },

    getClassList: function (major) {
      this.personInfoUpdate.u_className = ""
      if (major != "") {
        this.classList = [];
        this.chooseMajor = true;
        let value = "";
        this.majorList.forEach((item) => {
          if (item.discipline == major) {
            this.value = item.major;
          }
        });
        var that = this;
        axios({
          headers: { Authorization: this.print.Authorization },
          method: "get",
          url: "/api/major?major=" + this.value,
        }).then(function (reponse) {
          reponse.data.data.forEach((item) => {
            if (item.discipline == major) {
              that.classList.push(item.className);
            }
          });
        });
      }
    },

    updateInformation: function (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var that = this;
          //判断是录入还是更新
          //只要有一个空值就是录入
          if (this.personInfo.realName == "") {
            console.log("录入 post");
            //录入 post请求
            axios({
              method: "post",
              url: "/api/userInfo",
              headers: {
                Authorization: this.print.Authorization,
                "Content-Type":
                  "application/x-www-form-urlencoded; charset=UTF-8",
              },
              params: {
                username: this.print.username,
                realName: this.personInfoUpdate.u_realName,
                className: this.personInfoUpdate.u_className,
                stuNo: this.personInfoUpdate.u_stuNo,
                major: this.personInfoUpdate.u_major,
                identificationNumber: this.personInfoUpdate
                  .u_identificationNumber,
                mySchool: this.personInfoUpdate.u_mySchool,
                myArea: this.personInfoUpdate.u_myArea,
                myEducation: this.personInfoUpdate.u_myEducation,
                myDepartment: this.personInfoUpdate.u_myDepartment,
                mySex: this.personInfoUpdate.u_mySex,
                myIdType: this.personInfoUpdate.u_myIdType,
              },
            }).then(
              function (reponse) {
                that.$message({
                  message: "更改成功",
                  type: "success",
                });
                that.$router.go(0);
              },
              function (err) {
                that.$message.error("更改失败");
              }
            );
          } else {
            console.log("更新 put");
            //更新 put请求
            axios({
              method: "put",
              url: "/api/userInfo",
              params: {
                username: this.print.username,
                realName: this.personInfoUpdate.u_realName,
                className: this.personInfoUpdate.u_className,
                stuNo: this.personInfoUpdate.u_stuNo,
                major: this.personInfoUpdate.u_major,
                identificationNumber: this.personInfoUpdate
                  .u_identificationNumber,
                mySchool: this.personInfoUpdate.u_mySchool,
                myArea: this.personInfoUpdate.u_myArea,
                myEducation: this.personInfoUpdate.u_myEducation,
                myDepartment: this.personInfoUpdate.u_myDepartment,
                mySex: this.personInfoUpdate.u_mySex,
                myIdType: this.personInfoUpdate.u_myIdType,
              },
              headers: {
                Authorization: this.print.Authorization,
                "Content-Type":
                  "application/x-www-form-urlencoded; charset=UTF-8",
              },
            }).then(
              function (reponse) {
                that.$message({
                  message: "更改成功",
                  type: "success",
                });
                that.$router.go(0);
              },
              function (err) {
                that.$message.error("更改失败");
              }
            );
          }
        } else {
          this.dialogVisible = false;
          return false;
        }
      });
    },

    uploadImg: function (file) {
      const isLt2M = file.size / 1024 / 1024 < 1;
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 1MB!");
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
      param.append("fileName", this.fileName);
      let config = {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: this.print.Authorization,
        }, //这里是重点，需要和后台沟通好请求头，Content-Type不一定是这个值
      }; //添加请求头
      var that = this;
      axios
        .all([
          axios.post("/api/common/aliyun", param, config),
          axios({
            headers: { Authorization: this.print.Authorization },
            method: "post",
            url: "/api/image",
            params: {
              imageName: this.fileData.name.slice(
                0,
                this.fileData.name.length - 4
              ),
              userId: this.userId.userId,
              tag: "Exam",
              url: "../../../assets/" + this.fileData.name.slice(0, this.fileData.name.length - 4)
                + this.userId.userId + ".jpg",
            },
          }),
        ])
        .then(
          axios.spread(function (aliyunResponse, infoResponse) {
            that.$message({
              message: "上传照片成功",
              type: "success",
            });
            that.reload();
          })
        );
    },

    deleteExam: function () {
      var that = this;
      this.imageId = this.imageFile[0].imageId;
      axios
        .all([
          axios({
            headers: { Authorization: this.print.Authorization },
            method: "delete",
            url: "/api/image?imageId=" + this.imageId,
          }),
          axios({
            headers: { Authorization: this.print.Authorization },
            method: "delete",
            url: "/api/common/aliyun?fileurl=" + this.imageUrl,
          }),
        ])
        .then(
          axios.spread(function (del1, del2) {
            that.$message({
              message: "删除照片成功",
              type: "success",
            });
            that.reload();
          })
        );
    },
  },
};
</script>
