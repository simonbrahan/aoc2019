val start = 248345
val end = 746315

for (candidate in start..end) {
    if (isValid(candidate)) {
        println(candidate)
    }
}

fun isValid(candidate: Int): Boolean {
    var last = -1
    var foundDouble = false

    for (digit in candidate.toString().map(Character::getNumericValue)) {
        if (digit < last) {
            return false
        }

        if (digit == last) {
            foundDouble = true
        }

        last = digit
    }

    return foundDouble
}
