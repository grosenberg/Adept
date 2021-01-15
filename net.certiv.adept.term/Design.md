# Design

~~~ dot

digraph M1{ 

    node[style=rounded shape=box width=1.1]

    data[shape=oval label=<<i>Data Structure</i>>]
    features[label="Identify\nFeatures"]
    edges[label="Edges"]
    relate[label="Relations"]
    shapes[label="Shapes"]
    analysis[label="Evaluation"]
    goal[label="Conclusion"]
    
    data -> features 
    features-> { edges relate }  
    { edges relate } -> shapes
     
    { relate shapes edges } -> analysis [color=blue label="" constraint=true] 
    analysis -> goal [color=green constraint=true]

    { rank=same; edges relate}
}

~~~
