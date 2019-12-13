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
    case program[cursor]
    when 1
        program[program[cursor+3]] = program[program[cursor+1]] + program[program[cursor+2]]
    when 2
        program[program[cursor+3]] = program[program[cursor+1]] * program[program[cursor+2]]
    end

    cursor += 4
end

puts program[0]
