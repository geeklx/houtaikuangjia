const useEqualsArray = function (array, otherArray) {
  // if the other array is a falsy value, return
  if (!otherArray) return false

  // compare lengths - can save a lot of time
  if (array.length !== otherArray.length) {
    return false
  }

  for (let i = 0, l = array.length; i < l; i++) {
    // Check if we have nested arrays
    if (array[i] instanceof Array && otherArray[i] instanceof Array) {
      // recurse into the nested arrays
      if (!array[i].equals(otherArray[i])) {
        return false
      }
    } else if (array[i] !== otherArray[i]) {
      // Warning - two different object instances will never be equal: {x:20} != {x:20}
      return false
    }
  }
  return true
}

// const useEqualsObject = function (object, otherObject) {
//   //For the first loop, we only check for types
//   for (propName in object) {
//     //Check for inherited methods and properties - like .equals itself
//     //https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/hasOwnProperty
//     //Return false if the return value is different
//     if (object.hasOwnProperty(propName) != otherObject.hasOwnProperty(propName)) {
//       return false
//     }
//     //Check instance type
//     else if (typeof object[propName] != typeof otherObject[propName]) {
//       //Different types => not equal
//       return false
//     }
//   }
//   //Now a deeper check using other objects property names
//   for (propName in otherObject) {
//     //We must check instances anyway, there may be a property that only exists in otherObject
//     //I wonder, if remembering the checked values from the first loop would be faster or not
//     if (object.hasOwnProperty(propName) != otherObject.hasOwnProperty(propName)) {
//       return false
//     } else if (typeof object[propName] != typeof otherObject[propName]) {
//       return false
//     }
//     //If the property is inherited, do not check any more (it must be equa if both objects inherit it)
//     if (!object.hasOwnProperty(propName))
//       continue
//
//     //Now the detail check and recursion
//
//     //object returns the script back to the array comparing
//     /**REQUIRES Array.equals**/
//     if (object[propName] instanceof Array && otherObject[propName] instanceof Array) {
//       // recurse into the nested arrays
//       if (!object[propName].equals(otherObject[propName]))
//         return false
//     } else if (object[propName] instanceof Object && otherObject[propName] instanceof Object) {
//       // recurse into another objects
//       //console.log("Recursing to compare ", object[propName],"with",otherObject[propName], " both named \""+propName+"\"");
//       if (!object[propName].equals(otherObject[propName]))
//         return false
//     }
//     //Normal value comparison for strings and numbers
//     else if (object[propName] != otherObject[propName]) {
//       return false
//     }
//   }
//   //If everything passed, let's say YES
//   return true
// }

export { useEqualsArray }
