# NLP
This project requires a stanford corenlp model jar located in ${project.basedir}/lib/stanford-corenlp-3.6.0-models.jar (Can be downloaded from http://stanfordnlp.github.io/CoreNLP/)
#Some examples
Using Londons' wikipedia page
$ target/appassembler/bin/nlp -f london.txt -a tree
-> city/NN (root)
  -> London/NNP (nsubj)
    -> capital/NN (appos)
      -> England/NNP (nmod:poss)
        -> 's/POS (case)
      -> set/VBN (acl)
        -> Thames/NNP (nmod:on)
          -> on/IN (case)
          -> the/DT (det)
          -> River/NNP (compound)
  -> is/VBZ (cop)
  -> a/DT (det)
  -> 21st-century/JJ (amod)
  -> history/NN (nmod:with)
    -> with/IN (case)
    -> stretching/VBG (acl)
      -> back/RB (advmod)
      -> times/NNS (nmod:to)
        -> to/TO (case)
        -> Roman/JJ (amod)

$ target/appassembler/bin/nlp -f london.txt -a relations
1.0 | London | be | city
1.0 | England | have | capital
1.0 | London Eye observation wheel | provide | panoramic view

$ target/appassembler/bin/nlp -f london.txt -a locations
Thames
London
England

$ target/appassembler/bin/nlp -f london.txt -a located-in
RelationMention [type=Located_In, start=0, end=3, {Located_In, 0.5971903707416276; _NR, 0.40034252480618676; OrgBased_In, 0.0017920586346564786; Work_For, 3.7524012258531293E-4; Live_In, 2.998056949436563E-4}
	EntityMention [type=LOCATION, objectId=EntityMention-1, hstart=0, hend=1, estart=0, eend=1, headPosition=0, value="London", corefID=-1]
	EntityMention [type=LOCATION, objectId=EntityMention-2, hstart=2, hend=3, estart=2, eend=3, headPosition=2, value="England", corefID=-1]
]
