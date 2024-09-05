import { ref, computed, reactive } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCounterStore = defineStore("main", () => {
  let user = reactive({});

  interface User {
    id: string;
    name: string;
    email: string;
    created_date: string;
    phoneNumber: string;
    photo?: string;
  }
  let userList = ref<User[]>([]);

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

  const findAll = async () => {
    try {
      const res = await axios.get(`api/get-all`);
      userList.value = res.data.userList;
      return res.data;
    } catch (error) {
      throw error;
    }
  };

  const deleteUser = async (id: string, date: string) => {
    try {
      const res = await axios.delete(`api/${id}/${date}`);
      userList.value = userList.value.filter(
        (user) => user.id !== id && user.created_date !== date
      );
      return res.data;
    } catch (error) {
      throw error;
    }
  };
  return { user, login, signUp, userList, findAll, deleteUser };
});
