import { ref, computed, reactive } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCounterStore = defineStore("main", () => {
  let user = reactive({});

  const login = async (obj: Object) => {
    try {
      const res = await axios.post(`api/login`, obj);
      Object.assign(user, res.data.user);
      return res.data;
    } catch (error) {
      throw error;
    }
  };

  const signUp = async (obj: Object) => {
    try {
      const res = await axios.post(`api/sign-up`, obj);
      return res.data;
    } catch (error) {
      throw error;
    }
  };
  return { user, login, signUp };
});
