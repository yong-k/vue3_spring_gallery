<script setup>
import getNumberFormatted from '@/scripts/lib'
defineProps(['item'])
</script>

<template>
    <div class="card shadow-sm">
        <img :src="item.imgPath" class="img">
        <div class="card-body">
            <p class="card-text">
                <span>{{ item.name }} &nbsp;</span>
                <span class="discount badge bg-danger">
                    {{ item.discountPer }}%
                </span>
            </p>
            <div class="d-flex justify-content-between align-items-center">
                <button class="btn btn-primary">구입하기</button>
                <small class="price text-muted">
                    {{ getNumberFormatted(item.price) }}원
                </small>
                <small class="discount text-danger">
                    {{ getNumberFormatted(item.price - (item.price * item.discountPer / 100)) }}원
                </small>
            </div>
        </div>
    </div>
</template>

<style>
.card .img {
  display: inline-block;
  width: 100%;
  height: 250px;
}

.card .card-body .price {
  text-decoration: line-through;
}
</style>