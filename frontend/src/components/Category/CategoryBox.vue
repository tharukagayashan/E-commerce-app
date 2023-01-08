<template>
    <div class="card w-100 h-100">
        <div class="embed-responsive embed-responsive-16by9">
            <img class="card-img-top" :src="category.imageUrl" alt="Image" />
        </div>
        <div class="card-body">
            <h5 class="card-title">{{ category.categoryName }}</h5>
            <p class="card-text">{{ category.description }}</p>
            <a href="/admin/category/edit" class="btn btn-primary w-100">Edit</a>
        </div>
    </div>
</template>
<script>
export default {
    name: "CategoryBox",
    props: ["category"],
    methods: {},
}
</script>

<style scoped>
.card-img-top {
    object-fit: cover;
}
</style>