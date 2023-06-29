<template>
  <div class="container">
    <el-table :data="registrationList.slice((currentPage - 1) * pagesize, currentPage * pagesize)
      " style="width: 100%" :default-sort="{ prop: 'date', order: 'descending' }">
      <el-table-column prop="examDescription" label="考试类型" align="center">
      </el-table-column>
      <el-table-column prop="number" label="总名额" sortable align="center" width="100">
      </el-table-column>
      <el-table-column prop="last" label="剩余名额" sortable align="center" width="120">
      </el-table-column>
      <el-table-column prop="place" label="考场" align="center" width="150">
      </el-table-column>
      <el-table-column prop="time" label="时间" :formatter="termFormatter" sortable align="center">
      </el-table-column>
      <el-table-column label="状态" prop="stateUTF" align="center" width="150">
        <template slot-scope="scope">
          <template>
            <el-tag :type="scope.row.type">{{ scope.row.stateUTF }}</el-tag>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button @click="RegDialog = true" size="small">报名</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @current-change="handleCurrentChange" :current-page="currentPage" background align="center"
      layout="total, prev, pager, next, jumper" :total="pageTotal">
    </el-pagination>

    <el-dialog :visible.sync="RegDialog" width="400px">
      <el-row style="font-size: medium;">报名信息确认：</el-row>
      <el-row style="padding-top: 15px;">姓名：唐鹏程</el-row>
      <el-row>考试地点：南开大学津南校区</el-row>
      <el-row style="padding-bottom: 15px;">考试时间：2023-04-23</el-row>
      <el-button size="small" type="primary" style="margin-left: 200px;margin-top: 20px;"
        @click="RegDialog = false">确定报名</el-button>
      <el-button size="small" style="margin-top: 20px;" @click="RegDialog = false">取消</el-button>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  inject: ["reload"],
  name: "publicGetExam",
  data() {
    return {
      loading: false,
      RegDialog: false,
      //报名表
      registrationList: [
        {
          examDescription: '大学生英语四级考试',
          number: '50',
          last: '5',
          place: '南开大学津南校区',
          time: '2023-04-22',
          stateUTF: '报名已结束'
        },
        {
          examDescription: '大学生英语六级考试',
          number: '50',
          last: '10',
          place: '南开大学津南校区',
          time: '2023-04-23',
          stateUTF: '正在报名'
        },
        {
          examDescription: '大学生英语四级考试',
          number: '50',
          last: '12',
          place: '南开大学八里台校区',
          time: '2023-04-22',
          stateUTF: '报名已结束'
        },
        {
          examDescription: '大学生英语六级考试',
          number: '50',
          last: '4',
          place: '南开大学八里台校区',
          time: '2023-04-23',
          stateUTF: '正在报名'
        },
        {
          examDescription: '大学生英语四级考试',
          number: '50',
          last: '24',
          place: '南开大学津南校区',
          time: '2023-06-24',
          stateUTF: '正在报名'
        },
        {
          examDescription: '大学生英语六级考试',
          number: '50',
          last: '50',
          place: '南开大学津南校区',
          time: '2023-06-25',
          stateUTF: '报名未开始'
        },
        {
          examDescription: '大学生英语四级考试',
          number: '50',
          last: '0',
          place: '南开大学八里台校区',
          time: '2023-06-24',
          stateUTF: '人数已满'
        },
        {
          examDescription: '大学生英语六级考试',
          number: '50',
          last: '50',
          place: '南开大学八里台校区',
          time: '2023-06-25',
          stateUTF: '报名未开始'
        },
      ],

      //考试信息表
      examList: [],
      //初始页
      currentPage: 1,
      //每页的数据
      pagesize: 10,
      //数组总数
      pageTotal: 100000,
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

  }
};
</script>