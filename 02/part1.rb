def get_program
    File.read("input.txt")
        .strip
        .split(",")
        .map(&:to_i)
end

program = get_program

program[1] = 12
program[2] = 2

cursor = 0

while program[cursor] != 99
    a, b, dest = program[cursor+1..cursor+3]

    case program[cursor]
    when 1
        program[dest] = program[a] + program[b]
    when 2
        program[dest] = program[a] * program[b]
    end

    cursor += 4
end

puts program[0]
