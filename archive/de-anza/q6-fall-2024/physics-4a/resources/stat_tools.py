from math import sqrt

def average(nums: list):
    return sum(nums) / len(nums)


def rand_uncertainty(data: list):
    sum = 0
    for num in data:
        sum += (num - average(data)) ** 2

    return sqrt((1 / len(data)) * sum)