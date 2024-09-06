import { ref, computed, reactive } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCounterStore = defineStore("main", () => {
  let user = reactive({});
  const isTrue = ref<boolean>(false);

  interface User {
    id: string;
    name: string;
    email: string;
    created_date: string;
    phoneNumber: string;
    password?: string;
    photo?: string;
  }
  let userList = ref<User[]>([]);
  const selectedUser = ref<User>();

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

  const updateUser = async (obj: User) => {
    try {
      const res = await axios.put(`api/${obj.id}/${obj.created_date}`, obj);
      const index = userList.value.findIndex((user) => user.id === obj.id);
      if (index !== -1) {
        userList.value[index] = res.data.user;
        isTrue.value = false;
      }
      return res.data;
    } catch (error) {
      throw error;
    }
  };

  const modifyProfilePhoto = async (id: string, date: string, file: File) => {
    try {
      const formData = new FormData();
      formData.append("id", id);
      formData.append("date", date);
      formData.append("file", file);
      const res = await axios.put(`api/change-profile`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      const index = userList.value.findIndex(
        (user) => user.id === id && user.created_date === date
      );
      if (index !== -1) {
        userList.value[index] = res.data.user;
      }
      return res.data;
    } catch (error) {
      throw error;
    }
  };

  const searchHandler = async (name: string) => {
    try {
      const res = await axios.post(`api/search`, name);
      return res.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    user,
    login,
    signUp,
    userList,
    findAll,
    deleteUser,
    isTrue,
    selectedUser,
    updateUser,
    modifyProfilePhoto,
    searchHandler,
  };
});
