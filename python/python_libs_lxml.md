# python libs lxml

创建树:

```python
from lxml import etree
root = etree.Element("root")
root.append(etree.Element("child1"))
child2 = etree.SubElement(root, "child2")
```

节点查看:

```python
root.tag
#tostring -> b'abcdefg', you need b'abcdefg'.decode() to convert it to unicode
etree.tostring(root, pretty_print=True)
root[0]; root[:1]; root[-1:] #Elements as  lists
len(root)
root.index("child2")
list(root)
root.insert(0, etree.Element("child0"))
etree.iselement(root)
root[0] = root[-1] #move not copy
child2.getparent(); child2.getprevious(); child2.getnext()

from copy import deepcopy
element = etree.Element("neu")
element.append(deepcopy(root[1]))

#attributes as dict
root = etree.Element("root", interesting="totally")
root.get("interesting")
root.set("hello", "huhu")
root.keys(); root.items()

#attributes present a 'real' dict
attributes = root.attrib
attributes[key]; attributes[key] = value;

#get a snapshot of the attributes
d = dict(root.attrib)

#text
root.text = "hello world"
```

xml解析

```python
#document-style or mixed-content xml(such as xhtml). Up is data-centric xml.
html = etree.Element("html")
body = etree.SubElement(html, "body")
body.text = "hello"
br = etree.SubElement(body, "br")
br.tail = "world"
etree.tostring(br, with_tail=False)
etree.tostring(html, method="text")

#XPath
html.xpath("string()")
html.xpath("//text()")
build-text-list = etree.XPath("//text()"); #just text() not string()
texts = build-text-list(html); str = texts[0]; str.getparent(); str.is_text; str.is_tail
#<root><child>child1</child><child>child2</child><another>child3</another></root>
for element in root.iter(): #iter("another", "child")
    print("%s - %s" % (element.tag, element.text))

#iter all levels nodes( include Element, ProcessingInstructions, Comments and Entity)
root.append(etree.Entity("#234"))
root.append(etree.Comment("some comment"))
for element in root.iter(tag=etree.Element): pass

#unicode -> str, utf-8/iso-8859-1 -> bytes
root = etree.XML('<root><a><b/></a></root>')
etree.tostring(root, xml_declaration=Tree, encoding='iso-8859-1', method='xml/html/text'))
#'text' -> ascii
#ElementTree is a document wrapper with a root node, 'root' is just a Element.
#a tree include top-level processing instructions and comments, DOCTYPE, DTD
tree = etree.ElementTree(root)
tree.docinfo.xml_version; tree.docinfo.doctype;
tree.getroot() == root

#Parsing from strings and files.
# strings -> fromstring() XML() HTML() return Element input bytes
# file or file-like -> parse() return ElementTree
etree.fromstring("<root>data</root>")
parser = etree.XMLParser(remove_blank_text=True)
root = etree.XML("<root>   <a/>   <b>  </b>   </root>", parser)
#remove empty text between tags, not inside tags.

#Incremental parsing. step-by-step parse. ->
# call read().will block and wait until data becomes available.
class DataSource:
    data = [b"<roo", b"t><", b"a/", b"><", b"/root>"]
    def read(self, requested_size):
        try:
            return self.data.pop(0)
        except IndexError:
            return b''
tree = etree.parse(DataSource())

#feed(data), close()
parser = etree.XMLParser()
parser.feed("<roo"); parser.feed("t><"); ...
root = parser.close() #data must be right in xml syntax.
```
