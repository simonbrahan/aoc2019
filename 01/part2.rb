def get_fuel_for_mass(mass)
    required_fuel = (mass / 3).floor - 2

    return 0 if required_fuel <= 0

    return required_fuel + get_fuel_for_mass(required_fuel)
end

File.open("input.txt") do |f|
    puts f.collect { |num| get_fuel_for_mass(num.to_i) }.reduce(:+)
end
