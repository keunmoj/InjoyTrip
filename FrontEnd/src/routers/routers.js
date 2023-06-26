import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

import MainPage from "@/components/MainPage";
import LoginRegistPage from "@/components/user/LoginRegistPage";
import MyTrip from "@/components/user/MyTrip";
import UserInfo from "@/components/user/UserInfo";
import UserPwdUpdate from "@/components/user/UserPwdUpdate";
import TripMain from "@/components/trip/TripMain";
import BoardMain from "@/components/board/BoardMain";
// import PwdCheck from "@/components/common/PwdCheck";
import FooterPage from "@/components/common/FooterPage";
import NavBar from "@/components/common/NavBar";

export default new VueRouter({
  routes: [
    {
      path: "/",
      components: {
        NavBar: NavBar,
        default: MainPage,
        // Footer: FooterPage,
      },
    },
    {
      path: "/main",
      components: {
        NavBar: NavBar,
        default: MainPage,
        // Footer: FooterPage,
      },
    },
    {
      path: "/login",
      components: {
        NavBar: NavBar,
        default: LoginRegistPage,
        Footer: FooterPage,
      },
    },
    {
      path: "/mytrip",
      components: {
        NavBar: NavBar,
        default: MyTrip,
        Footer: FooterPage,
      },
    },
    {
      path: "/userpwdupdate",
      components: {
        NavBar: NavBar,
        default: UserPwdUpdate,
        Footer: FooterPage,
      },
    },

    {
      path: "/userinfo",
      components: {
        NavBar: NavBar,
        default: UserInfo,
        Footer: FooterPage,
      },
    },
    {
      path: "/board",
      components: {
        NavBar: NavBar,
        default: BoardMain,
        Footer: FooterPage,
      },
    },
    {
      path: "/trip",
      components: {
        NavBar: NavBar,
        default: TripMain,
        // Footer: FooterPage,
      },
    },
  ],
});
