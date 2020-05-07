setcolor(){
        printf $1
}

resetcolors(){
        printf "\e[39m"
}

startserver(){
        setcolor "\e[95m"
        echo "Starting $1..."
        resetcolors
        screen -dmS $1 /home/minecraft/$1/iniciar.sh
}

alias ..='cd ..'
alias ...='cd ../..'
alias ll='ls -l'
alias la='ls -la'
