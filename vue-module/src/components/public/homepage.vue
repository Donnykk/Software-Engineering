<template>
  <div>
    <el-carousel :interval="4000" type="card" height="420px">
      <el-carousel-item
        v-for="(item, index) in fiveImage"
        :key="index"
        class="carousel_image_type"
      >
        <img :src="item.url" style="height: 100%; width: 100%" />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
import axios from "axios";
import { mapState, mapActions } from "vuex";
//本地图片引入
import localImg1 from "../../assets/localImg1.jpg";
import localImg2 from "../../assets/localImg2.jpg";
export default {
  inject: ["searchMessage"],
  name: "homepage",
  data() {
    return {
      //消息列表
      messageList: [],
      //显示的消息列表，只要五条
      fiveMessage: [],
      //图片列表
      imageList: [],
      //显示图片列表，只要五条
      fiveImage: [],
      //消息时间列表
      dateList: [],
      //选择的消息列表
      selectMessage: [],
      //初始全部
      activeName: "all",
    };
  },
  computed: {
    ...mapState({
      print: (state) => state.print.all,
      userId: (state) => state.userId.all,
    }),
  },
  mounted: function () {
    this.getHomepageCommont();
  },
  methods: {
    getHomepageCommont: function () {
      var that = this;
      axios
        .all([
          axios({
            method: "get",
            url: "/api/image/tag?tag=Show",
          }),
          axios({
            method: "get",
            url: "/api/carousel?pageNum=&pageSize=1000000",
          }),
        ])
        .then(
          axios.spread(function (imageResponse, messageResponse) {
            //消息返回处理
            that.messageList = messageResponse.data.data;
            for (var i = 0; i < 5; i++) {
              that.fiveMessage.push(that.messageList.pop());
            }
            that.fiveMessage.forEach((item, index) => {
              if (item.label == "网站相关") that.$set(item, "type", "primary");
              else if (item.label == "考试相关")
                that.$set(item, "type", "warning");
              else if (item.label == "其他") that.$set(item, "type", "info");
              //每个日期赋值给dateList
              that.dateList[index] = item.subDate;
            });
            that.selectMessage = that.fiveMessage;
            //去除日期列表重复项
            that.dateList = that.unique(that.dateList);
            //图片返回处理，如果空，显示本地图片（两张）
            that.imageList = imageResponse.data.data;
            if (that.imageList != [])
              for (var i = 0; i < 5; i++) {
                that.fiveImage.push(that.imageList.pop());
              }
            else that.fiveImage = [{ url: localImg1 }, { url: localImg2 }];
          })
        );
    },

    unique(date) {
      const res = new Map();
      return date.filter((date) => !res.has(date) && res.set(date, 1));
    },

    getMore: function () {
      this.searchMessage();
    },

    handleClick(tab) {
      if (tab.name == "all") this.selectMessage = this.fiveMessage;
      else
        this.selectMessage = this.fiveMessage.filter(
          (item) => item.subDate == tab.name
        );
    },
  },
};
</script>

<style>
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.carousel_image_type {
  width: 50%;
}
</style>
