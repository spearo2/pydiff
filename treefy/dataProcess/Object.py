class VectorNode:
    def __init__(self, meta: list[str], name: str, level: int):
        self.vector = name
        self.vec_list = meta
        self.level = level
