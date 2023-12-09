@org.hibernate.annotations.GenericGenerator(name = "ID_GENERATOR", strategy = "enhanced-sequence", parameters = {
        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ID_GENERATOR"),
        @org.hibernate.annotations.Parameter(name = "initial_value", value = "10000")
})

package dev.renankrz.library;
