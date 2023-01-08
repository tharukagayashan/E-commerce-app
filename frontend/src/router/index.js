import { createRouter, createWebHistory } from 'vue-router';
import AddCategory from '../views/Category/AddCategory.vue';
import CategoryView from '../views/Category/CategoryView.vue';

const routes = [
  {
    path:'/admin/category/add',
    name:'AddCategory',
    component:AddCategory
  },
  {
    path:'/admin/category',
    name:'CategoryView',
    component:CategoryView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
