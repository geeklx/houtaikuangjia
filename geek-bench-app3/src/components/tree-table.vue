<template>
  <fs-table
    style="width: 100%"
    row-key="id"
    border
    class="background-table"
    :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    v-bind="$attrs"
    v-on="$listeners"
  >
    <slot></slot>
  </fs-table>
</template>

<script>
export default {
  name: 'treeTable'
}
</script>
