import { defineStore } from 'pinia'

export const useAccountStore = defineStore('accountStore', {
  state: () => ({ 
    account: {
        id: 0
    }
  }),
  getters: {},
  actions: {
    setAccount(payload) {
        this.account.id = payload
        //--→ payload 값으로 id 세팅하겠다
    }
  }
})