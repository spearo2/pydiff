from dataProcess.Object import VectorNode
from anytree import AnyNode, RenderTree


class DataProcessor:
    def __init__(self):
        self.trees = []

    def process_line(self, vector, col):
        vector_list = vector.split('|')
        vector_list.remove('')
        root_exist = False
        node_p = None
        node_c = None
        #print(vector_list)
        for root in self.trees:
            if vector_list[0] == root.name:
                root_exist = True
        level = 1
        if root_exist:
            for scalar in vector_list:
                if level == 1:
                    for root in self.trees:
                        if vector_list[0] == root.name:
                            node_c = root.children
                            node_p = root
                else:
                    node_exist = False
                    for node in node_c:
                        if node.name == scalar:
                            node_exist = True
                            node_c = node.children
                            node_p = node
                    if not node_exist:
                        if level == len(vector_list):
                            new_node = AnyNode(name=scalar, cpc=col[0], pc=col[1], file_path=col[2], depth=level, parent=node_p)
                        else:
                            new_node = AnyNode(name=scalar, cpc=None, pc=None, file_path=None, depth=level, parent=node_p)
                        node_p = new_node
                level = level + 1

        else:
            for scalar in vector_list:
                if level == 1:
                    if level == len(vector_list):
                        node_p = AnyNode(name=scalar, cpc=col[0], pc=col[1], file_path=col[2], depth=level, parent=node_p)
                    else:
                        node_p = AnyNode(name=scalar,  cpc=None, pc=None, file_path=None, depth=level, parent=node_p)
                    self.trees.append(node_p)
                else:
                    if level == len(vector_list):
                        new_node = AnyNode(name=scalar, cpc=col[0], pc=col[1], file_path=col[2], depth=level, parent=node_p)
                    else:
                        new_node = AnyNode(name=scalar, cpc=None, pc=None, file_path=None,  depth=level, parent=node_p)
                    node_p = new_node
                level = level + 1



