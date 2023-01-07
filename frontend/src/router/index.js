import { createRouter, createWebHistory } from 'vue-router';
import AddCategory from '../views/Category/AddCategory.vue';

const routes = [
  {
    path:'/admin/category/add',
    name:'AddCategory',
    component:AddCategory
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
